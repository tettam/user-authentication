package com.marcotettamanti.userauthentication.exceptions;

public class UserNotFoundException extends RuntimeException {
  
  public UserNotFoundException(String message){
    super(message);
  }
}
