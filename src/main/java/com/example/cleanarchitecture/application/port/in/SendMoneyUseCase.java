package com.example.cleanarchitecture.application.port.in;

public interface SendMoneyUseCase {

  public boolean sendMoney(SendMoneyCommand command);
}
