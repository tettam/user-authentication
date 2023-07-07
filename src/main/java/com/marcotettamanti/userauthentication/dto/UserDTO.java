package com.marcotettamanti.userauthentication.dto;

import org.springframework.beans.BeanUtils;

import com.marcotettamanti.userauthentication.model.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "Nome é obrigatório")
  @NotEmpty(message = "Nome não pode estar vazio")
  @Size(max = 60)
  private String name;

  @NotNull(message = "Email é obrigatório")
  @NotEmpty(message = "Email não pode estar vazio")
  @Email(message = "Email inválido")
  private String email;
  private String phone;

  public UserDTO(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.phone = user.getPhone();
    this.email = user.getEmail();
  }

  public User convertDtoToUser(UserDTO dto){
    User user = new User();
    BeanUtils.copyProperties(dto, user);
    return user;
  }
}
