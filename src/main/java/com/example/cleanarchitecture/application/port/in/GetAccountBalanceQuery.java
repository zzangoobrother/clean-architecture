package com.example.cleanarchitecture.application.port.in;

import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Money;

public interface GetAccountBalanceQuery {

  public Money getAccountBalance(AccountId accountId);
}
