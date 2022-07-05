package com.example.cleanarchitecture.adapter.persistence;

import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Activity;
import com.example.cleanarchitecture.domain.Activity.ActivityId;
import com.example.cleanarchitecture.domain.ActivityWindow;
import com.example.cleanarchitecture.domain.Money;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public Account mapToDomainEntity(AccountJpaEntity account, List<ActivityJpaEntity> activities, Long withdrawalBalance, Long depositBalance) {
    Money baselineBalance = Money.subtract(Money.of(depositBalance), Money.of(withdrawalBalance));

    return  Account.withId(new AccountId(account.getId()), baselineBalance, mapToActivityWindow(activities));
  }

  ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
    List<Activity> mappedActivities = new ArrayList<>();

    for (ActivityJpaEntity activity : activities) {
      mappedActivities.add(new Activity(
          new ActivityId(activity.getId()),
          new AccountId(activity.getOwnerAccountId()),
          new AccountId(activity.getSourceAccountId()),
          new AccountId(activity.getTargetAccountId()),
          activity.getTimestamp(),
          Money.of(activity.getAmount())));
    }

    return new ActivityWindow(mappedActivities);
  }

  public ActivityJpaEntity mapToJpaEntity(Activity activity) {
    return new ActivityJpaEntity(
        activity.getId() == null ? null : activity.getId().getActivityId(),
        activity.getTimestamp(),
        activity.getOwnerAccountId().getAccountId(),
        activity.getSourceAccountId().getAccountId(),
        activity.getTargetAccountId().getAccountId(),
        activity.getMoney().getAmount().longValue()
    );
  }
}
