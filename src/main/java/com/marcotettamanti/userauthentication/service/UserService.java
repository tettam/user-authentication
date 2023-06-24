package com.marcotettamanti.userauthentication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcotettamanti.userauthentication.dto.UserDTO;
import com.marcotettamanti.userauthentication.exceptions.UserExceptions;
import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  public List<UserDTO> findAll(){
    List<User> entity = repository.findAll();
    List<UserDTO> dtoList = new ArrayList<>();
    for (User obj : entity) {
      UserDTO dto = new UserDTO(obj);
      dtoList.add(dto);
    }

    return dtoList;
  }

  @Transactional
  public UserDTO findById(Long id){
    try {
      User entity = repository.findById(id).get();
      UserDTO dto = new UserDTO(entity);
      return dto;
      
    } catch (NoSuchElementException e) {
      e.printStackTrace();
      throw new  UserExceptions("Usuário não encontrado " + id);
    } 
  }

  public UserDTO save(User object){
    try {
      User entity = repository.save(object);
      UserDTO dto = new UserDTO(entity);
      return dto;

    } catch (Exception e) {
      throw new RuntimeException("Erro ao cadastrar usuário", e);
    } 
  }
}
