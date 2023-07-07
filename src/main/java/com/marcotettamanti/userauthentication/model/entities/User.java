package com.marcotettamanti.userauthentication.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails{
  
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Nome é obrigatório")
  @Size(max = 60)
  private String name;

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;
  private String phone;
  private String password;
  private LocalDateTime dateSendCode;
  private String codSecurity;

  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<UserPermission> userPermissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
   List<SimpleGrantedAuthority> authorities = new ArrayList<>();
   authorities.add(new SimpleGrantedAuthority("USER_ROLES"));
   return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }
  
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() { 
    return true;
  }

  @Override
  public boolean isAccountNonLocked() { 
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
     return true;
  }

  @Override
  public boolean isEnabled() {   
    return true;
  }
}
