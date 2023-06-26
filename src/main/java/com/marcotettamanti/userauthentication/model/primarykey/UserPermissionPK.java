package com.marcotettamanti.userauthentication.model.primarykey;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserPermissionPK implements Serializable {
  
  private Long userId;
  private Long permissionId;
}