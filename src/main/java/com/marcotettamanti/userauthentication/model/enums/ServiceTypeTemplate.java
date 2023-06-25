package com.marcotettamanti.userauthentication.model.enums;

public enum ServiceTypeTemplate {
  CONFIRMATION("email-registration-confirmation.html"),
  RECOVERY("email-recovery-cod-security.html");

  private final String template;
  
  private ServiceTypeTemplate(String template) {
    this.template = template;
  }

  public String getTemplate() {
    return template;
  }
}