package com.example.cleanarchitecture.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.cleanarchitecture.application.port.in.SendMoneyCommand;
import com.example.cleanarchitecture.application.port.out.AccountLock;
import com.example.cleanarchitecture.application.port.out.LoadAccountPort;
import com.example.cleanarchitecture.application.port.out.UpdateAccountStatePort;
import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Money;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendMoneyServiceTest {

  private final LoadAccountPort loadAccountPort = Mockito.mock(LoadAccountPort.class);
  private final AccountLock accountLock = Mockito.mock(AccountLock.class);
  private final UpdateAccountStatePort updateAccountStatePort = Mockito.mock(UpdateAccountStatePort.class);
  private final SendMoneyService sendMoneyService = new SendMoneyService(loadAccountPort, accountLock, updateAccountStatePort, new MoneyTransferProperties(Money.of(Long.MAX_VALUE)));

  @Test
  void transactionSucceeds() {
    Account sourceAccount = givenSourceAccount();
    Account targetAccount = givenTargetAccount();

    givenWithdrawalWillSucceed(sourceAccount);
    givenDepositWillSucceed(targetAccount);

    Money money = Money.of(500L);

    SendMoneyCommand command = new SendMoneyCommand(sourceAccount.getId().get(),
        targetAccount.getId().get(), money);

    boolean success = sendMoneyService.sendMoney(command);
    assertThat(success).isTrue();

    AccountId sourceAccountId = sourceAccount.getId().get();
    AccountId targetAccountId = targetAccount.getId().get();

    then(accountLock).should().lockAccount(eq(sourceAccountId));
    then(sourceAccount).should().withdraw(eq(money), eq(targetAccountId));
    then(accountLock).should().releaseAccount(eq(sourceAccountId));

    then(accountLock).should().lockAccount(eq(targetAccountId));
    then(targetAccount).should().deposit(eq(money), eq(sourceAccountId));
    then(accountLock).should().releaseAccount(eq(targetAccountId));
  }

  private Account givenSourceAccount() {
    return givenAnAccountWithId(new AccountId(41L));
  }

  private Account givenTargetAccount() {
    return givenAnAccountWithId(new AccountId(42L));
  }

  private Account givenAnAccountWithId(AccountId id) {
    Account account = Mockito.mock(Account.class);
    given(account.getId()).willReturn(Optional.of(id));
    given(loadAccountPort.loadAccount(eq(account.getId().get()), any(LocalDateTime.class))).willReturn(account);

    return account;
  }

  private void givenWithdrawalWillSucceed(Account account) {
    given(account.withdraw(any(Money.class), any(AccountId.class))).willReturn(true);
  }

  private void givenDepositWillSucceed(Account account) {
    given(account.deposit(any(Money.class), any(AccountId.class))).willReturn(true);
  }
}