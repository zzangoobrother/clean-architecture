package com.example.cleanarchitecture.adapter.persistence;

import java.time.LocalDateTime;
import javax.persistence.Column;
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
@Table(name = "activity")
@Entity
public class ActivityJpaEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private LocalDateTime timestamp;

  @Column
  private Long ownerAccountId;

  @Column
  private Long sourceAccountId;

  @Column
  private Long targetAccountId;

  @Column
  private Long amount;
}
