package com.marcotettamanti.userauthentication.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcotettamanti.userauthentication.dto.UserDTO;
import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.security.JwtUtil;
import com.marcotettamanti.userauthentication.service.UserDetailService;
import com.marcotettamanti.userauthentication.service.UserManagementService;
import com.marcotettamanti.userauthentication.service.UserService;
import com.marcotettamanti.userauthentication.utils.NameTitleCase;


@RestController
@RequestMapping(value = "api/users")
@CrossOrigin
public class UserController {

  @Autowired
  private UserService service;
  @Autowired
  private UserManagementService management;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private AuthenticationManager authorizationManager;
  @Autowired
  private UserDetailService userDetailService;

  @GetMapping
  public ResponseEntity<List<UserDTO>> findById(){
    List<UserDTO> userDto = service.findAll();
    return ResponseEntity.ok().body(userDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id){
    UserDTO userDto = service.findById(id);
    return ResponseEntity.ok().body(userDto);
  }

  @PostMapping("/management/new-user")
  public ResponseEntity<UserDTO> insert(@Validated @RequestBody UserDTO obj){
    String nameTitleCase = NameTitleCase.titleCase(obj.getName());
    obj.setName(nameTitleCase);
    UserDTO userDto = service.save(obj);
    return ResponseEntity.status(201).body(userDto);
  }

  @PostMapping("/management/new-cod")
  public String codRecovery(@RequestBody User user){
    String resultUpdatePassword = management.sendEmailCod(user.getEmail());
    return resultUpdatePassword;
  }

  @PostMapping("/management/change-password")
  public String changePassword(@RequestBody User user){
    String resultUpdatePassword = management.changePassword(user);
    return resultUpdatePassword;
  }

  @PostMapping("/management/login")
  public ResponseEntity<?> login(@RequestBody User user){
    UsernamePasswordAuthenticationToken usernamePasswordAuthentication =
      new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
      Authentication authentication = authorizationManager.authenticate(usernamePasswordAuthentication);
      
      User userAuthentication = (User) authentication.getPrincipal(); 
      String token = jwtUtil.generateTokenUsername(userAuthentication);
      HashMap<String, String> map = new HashMap<>();
      map.put("oken", token);
      return ResponseEntity.ok(map);

  }
}