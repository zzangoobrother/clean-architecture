package com.example.cleanarchitecture.application.port.in;

import org.springframework.stereotype.Service;

@Service
public interface SendMoneyUseCase {

  public boolean sendMoney(SendMoneyCommand command);
}
