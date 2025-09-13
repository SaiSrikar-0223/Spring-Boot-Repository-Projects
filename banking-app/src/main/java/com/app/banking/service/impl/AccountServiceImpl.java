package com.app.banking.service.impl;

import com.app.banking.dto.AccountDto;
import com.app.banking.entity.Account;
import com.app.banking.mapper.AccountMapper;
import com.app.banking.repository.AccountRepository;
import com.app.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {

         Account account=accountRepository
                 .findById(id)
                 .orElseThrow(()-> new RuntimeException("Account doesn't exist"));
         return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account=accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist"));

        double totalAmount = account.getBalance() + amount;
        account.setBalance(totalAmount);

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient Amount");
        }
        double totalAmount = account.getBalance() - amount;
        account.setBalance(totalAmount);

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) ->
                AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {

        Account account=accountRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("Account doesn't exist"));

        accountRepository.deleteById(id);

    }
}
