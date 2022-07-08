package com.example.cleanarchitecture.common;

import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Activity;
import com.example.cleanarchitecture.domain.Activity.ActivityBuilder;
import com.example.cleanarchitecture.domain.Money;
import java.time.LocalDateTime;

public class ActivityData {

  public static ActivityBuilder defaultActivity() {
    return Activity.builder()
        .ownerAccountId(new AccountId(42L))
        .sourceAccountId(new AccountId(42L))
        .targetAccountId(new AccountId(42L))
        .timestamp(LocalDateTime.now())
        .money(Money.of(999L));
  }

}
