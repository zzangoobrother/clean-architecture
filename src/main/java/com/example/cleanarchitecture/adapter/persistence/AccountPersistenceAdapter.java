package com.example.cleanarchitecture.adapter.persistence;

import com.example.cleanarchitecture.application.port.out.UpdateAccountStatePort;
import com.example.cleanarchitecture.application.port.out.LoadAccountPort;
import com.example.cleanarchitecture.domain.Account;
import com.example.cleanarchitecture.domain.Account.AccountId;
import com.example.cleanarchitecture.domain.Activity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

  private final AccountRepository accountRepository;
  private final ActivityRepository activityRepository;
  private final AccountMapper accountMapper;

  @Override
  public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
    AccountJpaEntity account = accountRepository.findById(accountId.getAccountId())
        .orElseThrow(EntityNotFoundException::new);

    List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
        accountId.getAccountId(), baselineDate);

    Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(accountId.getAccountId(), baselineDate));

    Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(accountId.getAccountId(), baselineDate));

    return accountMapper.mapToDomainEntity(account, activities, withdrawalBalance, depositBalance);
  }

  private Long orZero(Long value) {
    return value == null ? 0L : value;
  }

  @Override
  public void updateActivities(Account account) {
    for (Activity activity : account.getActivityWindow().getActivities()) {
      if (activity.getId() == null) {
        activityRepository.save(accountMapper.mapToJpaEntity(activity));
      }
    }
  }
}
