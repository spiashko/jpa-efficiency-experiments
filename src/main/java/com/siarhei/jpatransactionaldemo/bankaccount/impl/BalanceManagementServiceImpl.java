package com.siarhei.jpatransactionaldemo.bankaccount.impl;

import com.siarhei.jpatransactionaldemo.bankaccount.BalanceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BalanceManagementServiceImpl implements BalanceManagementService {

    private final BankAccountRepository customerRepository;
    private final BankAccountSearchEntityService searchService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void addToBalance(Long id, Long amount) {
        BankAccount customer = searchService.findOneEntityOrThrow(id);
        customer.setBalance(customer.getBalance() + amount);
        customerRepository.save(customer);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void subtractFromBalance(Long id, Long amount) {
        BankAccount customer = searchService.findOneEntityOrThrow(id);
        customer.setBalance(customer.getBalance() - amount);
        customerRepository.save(customer);
    }
}
