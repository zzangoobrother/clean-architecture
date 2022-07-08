package com.example.cleanarchitecture.domain;

import com.example.cleanarchitecture.domain.Account.AccountId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;

public class ActivityWindow {

  private List<Activity> activities = new ArrayList<>();

  public ActivityWindow(@NonNull List<Activity> activities) {
    this.activities = activities;
  }

  public Money calculateBalance(AccountId accountId) {
    Money depositBalance = activities.stream()
        .filter(activity -> activity.getTargetAccountId().equals(accountId))
        .map(Activity::getMoney)
        .reduce(Money.ZERO, Money::add);

    Money withdrawalBalance = activities.stream()
        .filter(activity -> activity.getSourceAccountId().equals(accountId))
        .map(Activity::getMoney)
        .reduce(Money.ZERO, Money::add);

    return Money.add(depositBalance, withdrawalBalance.negate());
  }

  public List<Activity> getActivities() {
    return Collections.unmodifiableList(this.activities);
  }

  public void addActivity(Activity activity) {
    this.activities.add(activity);
  }
}
