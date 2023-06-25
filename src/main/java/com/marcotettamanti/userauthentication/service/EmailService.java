package com.marcotettamanti.userauthentication.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.marcotettamanti.userauthentication.model.enums.ServiceTypeTemplate;

import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private Configuration fmConfiguration;
  
  @Value("${spring.mail.username}")
  private String email;

  public String sendEmail (String addressee, String title, String messageEmail) {

    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(email);
      message.setTo(addressee); 
      message.setSubject(title); 
      message.setText(messageEmail);
      javaMailSender.send(message);
      return "Email enviado com sucesso!";

    } catch (Exception e) {
        return "Erro ao enviar o email";
    }
  }  

  public void stylizedEmail(String addressee, String title, ServiceTypeTemplate messageType, Map<String, Object> properties){
   MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    try {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
    mimeMessageHelper.setFrom(email);
    mimeMessageHelper.setSubject(title);
    mimeMessageHelper.setTo(addressee);
    mimeMessageHelper.setText(getContextFromTemplate(messageType, properties), true);
    javaMailSender.send(mimeMessageHelper.getMimeMessage());

    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private String getContextFromTemplate(ServiceTypeTemplate messageType, Map<String,Object> model){
    StringBuffer content = new StringBuffer();

    try {
      content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate(messageType.getTemplate()), model));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return content.toString();
  }   
}