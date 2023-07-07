package com.marcotettamanti.userauthentication.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorMessage {

  private HttpStatus status;
  private List<String> errors;

  public ApiErrorMessage(HttpStatusCode status, List<String> errors) {
    this.status = (HttpStatus) status;
    this.errors = errors;
  }

   public ApiErrorMessage(HttpStatusCode status, String error) {
        super();
        this.status = (HttpStatus) status;
        errors = Arrays.asList(error);
    }
}