package com.marcotettamanti.userauthentication.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  @Autowired
  private PasswordEncoder passwordEncoder;

  public String sendEmailCod(String email){
    User user = repository.findByEmail(email);
    if(user == null){
      return "Email não cadastrado";
    }
    
    user.setCodSecurity(getCodRecoveryPassword(user.getId()));
    user.setDateSendCode(LocalDateTime.now());
    repository.saveAndFlush(user);
    Map<String , Object> properties = new HashMap<>();
    properties.put("name", user.getName());
    properties.put("message", "Será necessário seu código de segurança para alterar a senha.");
    properties.put("codSecurity" , user.getCodSecurity());
    //emailService.stylizedEmail(user.getEmail(), "Código de Segurança", ServiceTypeTemplate.RECOVERY,properties);
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

    String passwordEncrypt = passwordEncoder.encode(obj.getPassword());
    user.setPassword(passwordEncrypt);
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
