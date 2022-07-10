package com.example.cleanarchitecture.domain;

import java.math.BigInteger;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Money {

  public static Money ZERO = Money.of(0L);

  @NotNull
  private final BigInteger amount;

  public boolean isGreaterThanOrEqualTo(Money money) {
    return this.amount.compareTo(money.amount) >= 0;
  }

  public boolean isGreaterThan(Money money) {
    return this.amount.compareTo(money.amount) >= 1;
  }

  public static Money add(Money baselineBalance, Money calculateBalance) {
    return new Money(baselineBalance.amount.add(calculateBalance.amount));
  }

  public static Money of(Long amount) {
    return new Money(BigInteger.valueOf(amount));
  }

  public Money negate() {
    return new Money(this.amount.negate());
  }

  public boolean isPositive() {
    return this.amount.compareTo(BigInteger.ZERO) > 0;
  }

  public static Money subtract(Money a, Money b) {
    return new Money(a.amount.subtract(b.amount));
  }
}
