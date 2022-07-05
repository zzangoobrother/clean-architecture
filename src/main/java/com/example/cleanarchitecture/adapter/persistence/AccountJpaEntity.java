package com.example.cleanarchitecture.adapter.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class AccountJpaEntity {

  @Id
  @GeneratedValue
  private Long id;
}
