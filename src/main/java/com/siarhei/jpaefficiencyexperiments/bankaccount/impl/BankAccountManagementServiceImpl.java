package com.siarhei.jpaefficiencyexperiments.bankaccount.impl;

import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccount;
import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountCreationModel;
import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountManagementService;
import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountViewAModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BankAccountManagementServiceImpl implements BankAccountManagementService {

    private final BankAccountRepository repository;
    private final BankAccountCreationMapper creationMapper;
    private final BankAccountSearchMapper searchMapper;

    @Transactional
    @Override
    public BankAccountViewAModel createBankAccount(BankAccountCreationModel creationModel) {
        BankAccount bankAccount = creationMapper.map(creationModel);
        repository.save(bankAccount);
        return searchMapper.mapToViewA(bankAccount);
    }

    @Transactional
    @Override
    public void deleteBankAccountById(UUID id) {
        repository.deleteById(id);
    }
}
