package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.application.port.in.SendMoneyCommand;
import com.example.cleanarchitecture.application.port.in.SendMoneyUseCase;
import com.example.cleanarchitecture.application.port.out.AccountLock;
import com.example.cleanarchitecture.application.port.out.LoadAccountPort;
import com.example.cleanarchitecture.application.port.out.UpdateAccountStatePort;
import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.shared.UseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@UseCase
public class SendMoneyService implements SendMoneyUseCase {

  private final LoadAccountPort loadAccountPort;
  private final AccountLock accountLock;
  private final UpdateAccountStatePort updateAccountStatePort;
  private final MoneyTransferProperties moneyTransferProperties;

  @Override
  public boolean sendMoney(SendMoneyCommand command) {
    checkThreshold(command);

    LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

    Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
    Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

    AccountId sourceAccountId = sourceAccount.getId()
        .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
    AccountId targetAccountId = targetAccount.getId()
        .orElseThrow(() -> new IllegalStateException("expected target account Id not to be empty"));

    accountLock.lockAccount(sourceAccountId);
    if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
      accountLock.releaseAccount(sourceAccountId);
      return false;
    }

    accountLock.lockAccount(targetAccountId);
    if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
      accountLock.releaseAccount(sourceAccountId);
      accountLock.releaseAccount(targetAccountId);
      return false;
    }

    updateAccountStatePort.updateActivities(sourceAccount);
    updateAccountStatePort.updateActivities(targetAccount);

    accountLock.releaseAccount(sourceAccountId);
    accountLock.releaseAccount(targetAccountId);

    return true;
  }

  private void checkThreshold(SendMoneyCommand command) {
    if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
      throw new ThresholdExcessdedException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
    }
  }
}
