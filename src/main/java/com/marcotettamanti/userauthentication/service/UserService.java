package com.marcotettamanti.userauthentication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcotettamanti.userauthentication.dto.UserDTO;
import com.marcotettamanti.userauthentication.exceptions.UserNotFoundException;
import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.model.enums.ServiceTypeTemplate;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

@Service
public class UserService {
  
  @Autowired
  private UserRepository repository;

  @Autowired
  private EmailService email;

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
      throw new  UserNotFoundException("Usuário não encontrado " + id);
    } 
  }

  @Transactional
  public UserDTO save(UserDTO object){
    try {
      User verifyEmail = repository.findByEmail(object.getEmail());
      if(verifyEmail != null){
        throw new UserNotFoundException("Email já cadastrado");
      }
      User entity = new UserDTO().convertDtoToUser(object);
      UserDTO dto = new UserDTO(repository.saveAndFlush(entity));
      Map<String, Object> properties = new HashMap<>();
      properties.put("name", dto.getName());
      properties.put("message", "Seu cadastro foi criado com sucesso! Em breve você receberá um código de acesso para alterar sua senha.");
      //email.stylizedEmail(dto.getEmail(), "Cadastro Realizado", ServiceTypeTemplate.CONFIRMATION ,properties);
      return dto;

    } catch (DataAccessException e) {
      throw new UserNotFoundException("Erro ao cadastrar usuário " + e.getMessage());
    } 
  }
}