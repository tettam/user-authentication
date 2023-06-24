package com.marcotettamanti.userauthentication.dto;

import org.springframework.beans.BeanUtils;

import com.marcotettamanti.userauthentication.model.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private Long id;
  
  @NotBlank
  @Size(max = 60)
  private String name;

  @NotBlank
  @Email
  private String email;
  private String telephone;

  public UserDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.telephone = user.getTelephone();
    this.email = user.getEmail();
  }

  public User convertDtoToUser(UserDTO dto){
    User user = new User();
    BeanUtils.copyProperties(dto, user);
    return user;
  }
}
