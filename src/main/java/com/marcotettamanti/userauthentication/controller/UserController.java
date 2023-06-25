package com.marcotettamanti.userauthentication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcotettamanti.userauthentication.dto.UserDTO;
import com.marcotettamanti.userauthentication.service.UserManagement;
import com.marcotettamanti.userauthentication.service.UserService;


@RestController
@RequestMapping(value = "api/users")
public class UserController {

  @Autowired
  private UserService service;
  @Autowired
  private UserManagement management;

  @GetMapping
  public ResponseEntity<List<UserDTO>> findById(){
    List<UserDTO> userDto = service.findAll();
    return ResponseEntity.ok().body(userDto);
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id){
    UserDTO userDto = service.findById(id);
    return ResponseEntity.ok().body(userDto);
  }

  @PostMapping
  public ResponseEntity<UserDTO> insert(@Validated @RequestBody UserDTO obj){
    UserDTO userDto = service.save(obj);
    return ResponseEntity.status(201).body(userDto);
  }

  @PutMapping(value = "/management")
  public String updatePassword(@RequestBody UserDTO userDto){
    String resultUpdatePassword = management.sendEmailCod(userDto.getEmail());
    return resultUpdatePassword;
  }
}
