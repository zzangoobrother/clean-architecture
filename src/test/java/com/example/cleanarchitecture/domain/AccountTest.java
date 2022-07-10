package com.example.cleanarchitecture.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.cleanarchitecture.common.ActivityData;
import com.example.cleanarchitecture.domain.Account.AccountBuilder;
import com.example.cleanarchitecture.domain.Account.AccountId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  void withdrawalSucceeds() {
    AccountId accountId = new AccountId(1L);

    List<Activity> activities = new ArrayList<>();
    activities.add(ActivityData.defaultActivity()
        .targetAccountId(accountId)
        .money(Money.of(999L))
        .build());

    activities.add(ActivityData.defaultActivity()
        .targetAccountId(accountId)
        .money(Money.of(1L))
        .build());

    Account account = defaultAccount()
        .accountId(accountId)
        .baselineBalance(Money.of(555L))
        .activityWindow(new ActivityWindow(activities))
        .build();

    boolean success = account.withdraw(Money.of(555L), new AccountId(99L));

    assertThat(success).isTrue();
    assertThat(account.getActivityWindow().getActivities()).hasSize(3);
    assertThat(account.calculateBalance()).isEqualTo(Money.of(1000L));
  }

  private AccountBuilder defaultAccount() {
    return Account.builder()
        .accountId(new AccountId(42L))
        .baselineBalance(Money.of(999L))
        .activityWindow(new ActivityWindow(Arrays.asList(ActivityData.defaultActivity().build(), ActivityData.defaultActivity().build())));
  }
}