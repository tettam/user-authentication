package com.marcotettamanti.userauthentication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender javaMailSender;
  
  @Value("${spring.mail.username}")
  private String email;

  public String sendEmail (String from, String title, String messageEmail) {
    
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(email);
      message.setTo(from); 
      message.setSubject(title); 
      message.setText(messageEmail);
      javaMailSender.send(message);
      return "Email enviado com sucesso!";

    } catch (Exception e) {
        return "Erro ao enviar o email";
    }
  }  
}
