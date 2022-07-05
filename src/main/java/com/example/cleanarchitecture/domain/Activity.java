package com.example.cleanarchitecture.domain;

import static com.example.cleanarchitecture.domain.Account.*;

import com.example.cleanarchitecture.domain.Account.AccountId;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Activity {

  @Getter
  private ActivityId id;

  @Getter
  @NonNull
  private final AccountId ownerAccountId;

  @Getter
  @NonNull
  private final AccountId sourceAccountId;

  @Getter
  @NonNull
  private final AccountId targetAccountId;

  @Getter
  @NonNull
  private final LocalDateTime timestamp;

  @Getter
  @NonNull
  private final Money money;

  public Activity(@NonNull AccountId ownerAccountId, AccountId sourceAccountId, AccountId targetAccountId,
      LocalDateTime timestamp, Money money) {
    this.id = null;
    this.ownerAccountId = ownerAccountId;
    this.sourceAccountId = sourceAccountId;
    this.targetAccountId = targetAccountId;
    this.timestamp = timestamp;
    this.money = money;
  }

  @Value
  public static class ActivityId {
    private final Long activityId;
  }
}
