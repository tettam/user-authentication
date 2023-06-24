package com.marcotettamanti.userauthentication.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.service.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public ResponseEntity<List<User>> findById(){
    List<User> user = service.findAll();
    return ResponseEntity.ok().body(user);
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<User> findById(@PathVariable Long id){
    User user = service.findById(id);
    return ResponseEntity.ok().body(user);
  }

  @PostMapping
  public ResponseEntity<User> insert(@RequestBody User obj){
    User user = service.save(obj);
    return ResponseEntity.status(201).body(user);
  }
}
