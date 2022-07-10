package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.domain.Money;

public class ThresholdExcessdedException extends RuntimeException{

  public ThresholdExcessdedException(Money threshold, Money actual) {
    super(String.format("Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!", actual, threshold));
  }
}
