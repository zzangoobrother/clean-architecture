package com.example.cleanarchitecture.adapter.web;

import com.example.cleanarchitecture.application.port.out.AccountResource;
import com.example.cleanarchitecture.shared.WebAdapter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class AccountController {

//  private final GetAccountBalanceQuery getAccountBalanceQuery;
//  private final ListAccountsQuery listAccountsQuery;
//  private final LoadAccountQuery loadAccountQuery;
//
//  private final SendMoneyUseCase sendMoneyUseCase;
//  private final CreateAccountUseCase createAccountUseCase;

  @GetMapping("/accounts")
  public List<AccountResource> listAccounts() {
    return null;
  }

  @GetMapping("/accounts/id")
  public AccountResource getAccount(@PathVariable("accountId") Long accountId) {
    return null;
  }

  @GetMapping("/accounts/{id}/balance")
  public long getAccountBalance(@PathVariable("accountId") Long accountId) {
    return 0L;
  }

  @PostMapping("/accounts")
  public AccountResource createAccount(@RequestBody AccountResource account) {
    return null;
  }
}
