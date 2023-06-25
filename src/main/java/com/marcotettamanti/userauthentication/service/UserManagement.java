package com.marcotettamanti.userauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

@Service
public class UserManagement {
  
  @Autowired
  private UserRepository repository;

  public String sendEmailCod(String email){
    User user = repository.findByEmail(email);
    if(user == null){
      return "Usuário não encontrado";
    }
    return "Foi enviado um código de seguro para seu email";
  }
}
