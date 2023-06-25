package com.marcotettamanti.userauthentication.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

@Service
public class UserManagementService {
  
  @Autowired
  private UserRepository repository;
  @Autowired
  private EmailService emailService;

  public String sendEmailCod(String email){
    User user = repository.findByEmail(email);
    user.setCodSecurity(getCodRecoveryPassword(user.getId()));
    user.setDateSendCode(LocalDateTime.now());
    repository.saveAndFlush(user);
    emailService.sendEmail(user.getEmail(), "Código de alteração de senha", "Olá, o seu código para recuperação de senha é : " + user.getCodSecurity());
    return "Código enviado";
  }

  private String getCodRecoveryPassword(Long id){
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssmm");
    return dateTime.format(format)+id;
  }
}
