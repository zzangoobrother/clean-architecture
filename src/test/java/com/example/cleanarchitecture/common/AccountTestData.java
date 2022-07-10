package com.example.cleanarchitecture.common;

import static com.example.cleanarchitecture.common.ActivityData.defaultActivity;

import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountBuilder;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Activity;
import com.example.cleanarchitecture.domain.ActivityWindow;
import com.example.cleanarchitecture.domain.Money;
import java.util.ArrayList;
import java.util.List;

public class AccountTestData {

  public static AccountBuilder defaultAccount() {
    List<Activity> activities = new ArrayList<>();
    activities.add(defaultActivity().build());
    activities.add(defaultActivity().build());

    return Account.builder()
        .accountId(new AccountId(42L))
        .baselineBalance(Money.of(999L))
        .activityWindow(new ActivityWindow(activities));
  }
}
