package com.marcotettamanti.userauthentication.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcotettamanti.userauthentication.model.entities.User;
import com.marcotettamanti.userauthentication.repositoty.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserManagementService {
  public static final int CODE_TIME_SECONDS = 300;
  
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

  @Transactional
  public String changePassword(User obj){
    User user = repository.findByEmailAndCodSecurity(obj.getEmail(), obj.getCodSecurity());
    if(user == null){
      return "Email ou código inválido!";
    }
    Long minutesdiff = Duration.between(LocalDateTime.now(), user.getDateSendCode()).toSeconds();
    if(minutesdiff > CODE_TIME_SECONDS){
      return "Código de validação foi expirado, solicite um novo código!";
    }
    user.setPassword(obj.getPassword());
    user.setCodSecurity(null);
    repository.saveAndFlush(user);
    return "Senha alterada com sucesso!";
  }

  private String getCodRecoveryPassword(Long id){
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssmm");
    return dateTime.format(format)+id;
  }
}
