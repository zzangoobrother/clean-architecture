package com.example.cleanarchitecture.adapter.web;

import com.example.cleanarchitecture.application.port.in.SendMoneyCommand;
import com.example.cleanarchitecture.application.port.in.SendMoneyUseCase;
import com.example.cleanarchitecture.domain.AccountId;
import com.example.cleanarchitecture.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SendMoneyController {

  private final SendMoneyUseCase sendMoneyUseCase;

  @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
  public void sendMoney(@PathVariable("sourceAccountId") Long sourceAccountId, @PathVariable("targetAccountId") Long targetAccountId, @PathVariable("amount") Long amount) {
    SendMoneyCommand command = new SendMoneyCommand(new AccountId(sourceAccountId),
        new AccountId(targetAccountId), Money.of(amount));

    sendMoneyUseCase.sendMoney(command);
  }
}
