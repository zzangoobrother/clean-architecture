package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.application.port.in.GetAccountBalanceQuery;
import com.example.cleanarchitecture.domain.AccountId;
import com.example.cleanarchitecture.domain.Money;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {

  private final LoadAccountPort loadAccountPort;

  @Override
  public Money getAccountBalance(AccountId accountId) {
    return loadAccountPort.loadAccount(accountId, LocalDateTime.now()).calculateBalance();
  }
}
