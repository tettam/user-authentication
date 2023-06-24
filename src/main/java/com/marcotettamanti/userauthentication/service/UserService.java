package com.marcotettamanti.userauthentication.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

  @Transactional(readOnly = true)
  public List<UserDTO> findAll(){
    List<User> entity = repository.findAll();
    List<UserDTO> dtoList = new ArrayList<>();
    for (User obj : entity) {
      UserDTO dto = new UserDTO(obj);
      dtoList.add(dto);
    }

    return dtoList;
  }

  @Transactional(readOnly = true)
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

  @Transactional
  public UserDTO save(UserDTO object){
    try {
      User entity = new UserDTO().convertDtoToUser(object);
      UserDTO dto = new UserDTO(repository.saveAndFlush(entity));
      return dto;

    } catch (DataAccessException e) {
      throw new UserExceptions("Erro ao cadastrar usuário " + e.getMessage());
    } 
  }
}
