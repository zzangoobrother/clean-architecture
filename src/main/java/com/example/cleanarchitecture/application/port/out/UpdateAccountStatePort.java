package com.example.cleanarchitecture.application.port.out;

import com.example.cleanarchitecture.domain.Account;

public interface UpdateAccountStatePort {

  public void updateActivities(Account account);
}
