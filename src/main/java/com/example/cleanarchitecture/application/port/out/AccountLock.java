package com.example.cleanarchitecture.application.port.out;

import com.example.cleanarchitecture.domain.Account.AccountId;

public interface AccountLock {

  void lockAccount(AccountId accountId);

  void releaseAccount(AccountId accountId);
}
