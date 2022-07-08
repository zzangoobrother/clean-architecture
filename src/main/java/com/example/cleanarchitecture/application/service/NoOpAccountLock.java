package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.application.port.out.AccountLock;
import com.example.cleanarchitecture.domain.Account.AccountId;
import org.springframework.stereotype.Component;

@Component
public class NoOpAccountLock implements AccountLock {

  @Override
  public void lockAccount(AccountId accountId) {

  }

  @Override
  public void releaseAccount(AccountId accountId) {

  }
}
