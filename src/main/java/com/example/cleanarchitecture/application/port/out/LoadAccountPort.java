package com.example.cleanarchitecture.application.port.out;

import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import java.time.LocalDateTime;

public interface LoadAccountPort {

  public Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
