package com.example.cleanarchitecture.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

public class Account {

  @Getter
  private AccountId id;
  @Getter
  private Money baselineBalance;
  @Getter
  private ActivityWindow activityWindow;

  @Builder
  public Account(AccountId accountId, Money baselineBalance, ActivityWindow activityWindow) {
    this.id = accountId;
    this.baselineBalance = baselineBalance;
    this.activityWindow = activityWindow;
  }

  public static Account withoutId(Money baselineBalance, ActivityWindow activityWindow) {
    return new Account(null, baselineBalance, activityWindow);
  }

  public static Account withId(AccountId accountId, Money baselineBalance, ActivityWindow activityWindow) {
    return new Account(accountId, baselineBalance, activityWindow);
  }

  public Money calculateBalance() {
    return Money.add(this.baselineBalance, this.activityWindow.calculateBalance(this.id));
  }

  public boolean withdraw(Money money, AccountId targetAccountId) {
    if (!mayWithdraw(money)) {
      return false;
    }

    Activity withdrawal = new Activity(
        this.id,
        this.id,
        targetAccountId,
        LocalDateTime.now(),
        money
    );

    this.activityWindow.addActivity(withdrawal);
    return true;
  }

  private boolean mayWithdraw(Money money) {
    return Money.add(this.calculateBalance(), money.negate()).isPositive();
  }

  public boolean deposit(Money money, AccountId sourceAccountId) {
    Activity deposit = new Activity(
        this.id,
        sourceAccountId,
        this.id,
        LocalDateTime.now(),
        money
    );

    this.activityWindow.addActivity(deposit);
    return true;
  }

  @Value
  public static class AccountId {
    private Long accountId;
  }
}
