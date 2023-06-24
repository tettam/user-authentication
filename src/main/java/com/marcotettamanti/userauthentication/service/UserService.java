package com.marcotettamanti.userauthentication.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.marcotettamanti.userauthentication.exceptions.UserExceptions;
import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  public List<User> findAll(){
    return repository.findAll();
  }

  public User findById(Long id){
    
    try {
      return repository.findById(id).get();
      
    } catch (NoSuchElementException e) {
      e.printStackTrace();
      throw new  UserExceptions("Usuário não encontrado " + id);
    } 
  }

  public User save(User object){
    try {
       return repository.save(object);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao cadastrar usuário", e);
    } 
  }
}
