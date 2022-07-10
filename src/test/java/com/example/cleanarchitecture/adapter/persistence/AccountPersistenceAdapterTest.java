package com.example.cleanarchitecture.adapter.persistence;

import static com.example.cleanarchitecture.common.AccountTestData.defaultAccount;
import static com.example.cleanarchitecture.common.ActivityData.defaultActivity;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Activity;
import com.example.cleanarchitecture.domain.ActivityWindow;
import com.example.cleanarchitecture.domain.Money;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Import({AccountPersistenceAdapter.class, AccountMapper.class})
class AccountPersistenceAdapterTest {

  @Autowired
  private AccountPersistenceAdapter adapter;

  @Autowired
  private ActivityRepository activityRepository;

  @Test
  @Sql("AccountPersistenceAdapterTest.sql")
  void loadsAccount() {
    Account account = adapter.loadAccount(new AccountId(1L), LocalDateTime.of(2018, 8, 10, 0, 0));

    assertThat(account.getActivityWindow().getActivities()).hasSize(2);
    assertThat(account.calculateBalance()).isEqualTo(Money.of(500L));
  }

  @Test
  void updatesActivities() {
    List<Activity> activities = new ArrayList<>();
    activities.add(defaultActivity()
        .id(null)
        .money(Money.of(1L))
        .build());


    Account account = defaultAccount()
        .baselineBalance(Money.of(555L))
        .activityWindow(new ActivityWindow(activities))
        .build();

    adapter.updateActivities(account);
    assertThat(activityRepository.count()).isEqualTo(1);

    ActivityJpaEntity savedActivitiy = activityRepository.findAll().get(0);
    assertThat(savedActivitiy.getAmount()).isEqualTo(1L);
  }
}