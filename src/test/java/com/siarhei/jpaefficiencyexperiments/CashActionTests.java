package com.siarhei.jpaefficiencyexperiments;

import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountCreationModel;
import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountManagementService;
import com.siarhei.jpaefficiencyexperiments.bankaccount.BankAccountViewAModel;
import com.siarhei.jpaefficiencyexperiments.cash.*;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import net.ttddyy.dsproxy.QueryCountHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CashActionTests extends BaseApplicationTest {

    @Autowired
    private CashRefillCreationService cashRefillManagementService;
    @Autowired
    private CashRefillSearchService cashRefillSearchService;
    @Autowired
    private CashWithdrawalCreationService cashWithdrawalManagementService;
    @Autowired
    private CashWithdrawalSearchService cashWithdrawalSearchService;
    @Autowired
    private BankAccountManagementService bankAccountManagementService;

    @Test
    void givenOneAccount_whenCreateCashRefill_thenCreated() {
        //given
        BankAccountViewAModel createResponse =
                bankAccountManagementService.createBankAccount(BankAccountCreationModel.builder()
                        .balance(100L)
                        .build());
        SQLStatementCountValidator.reset();

        //when
        CashRefillViewBModel cashRefill = cashRefillManagementService.createCashAction(
                CashRefillCreationModel.builder()
                        .bankAccountId(createResponse.getId())
                        .cashAmount(100L)
                        .build());

        //then
        SQLStatementCountValidator.assertInsertCount(2);
        Assertions.assertEquals(4, QueryCountHolder.getGrandTotal().getTotal());

        Assertions.assertNotNull(cashRefill);
        Assertions.assertNotNull(cashRefill.getId());
        Assertions.assertEquals(100L, cashRefill.getCashAmount());
        BaseCashActionViewBModel.CashActionOperationModel operation = cashRefill.getCashRefillOperation();
        Assertions.assertNotNull(operation);
        Assertions.assertNotNull(operation.getId());
        Assertions.assertEquals(100L, operation.getAmount());
        Assertions.assertEquals(createResponse.getId(), operation.getBankAccountId());
    }

    @Test
    void givenCashRefill_whenFindCashRefillViewX_thenOnlyOneSqlExecuted() {
        //given
        BankAccountViewAModel bankAccount =
                bankAccountManagementService.createBankAccount(
                        BankAccountCreationModel.builder()
                                .balance(100L)
                                .build());
        CashRefillViewBModel createResponse =
                cashRefillManagementService.createCashAction(
                        CashRefillCreationModel.builder()
                                .bankAccountId(bankAccount.getId())
                                .cashAmount(100L)
                                .build());
        SQLStatementCountValidator.reset();

        //when-then
        AssertUtils.assertSelectCountExactlyOne(() -> cashRefillSearchService.findAll(CashRefillViewAModel.class));
        AssertUtils.assertSelectCountExactlyOne(() -> cashRefillSearchService.findAll(CashRefillViewBModel.class));

        AssertUtils.assertSelectCountExactlyOne(() -> cashRefillSearchService.findOneOrThrow(createResponse.getId(), CashRefillViewAModel.class));
        AssertUtils.assertSelectCountExactlyOne(() -> cashRefillSearchService.findOneOrThrow(createResponse.getId(), CashRefillViewBModel.class));
    }

    @Test
    void givenOneAccount_whenCreateCashWithdrawal_thenCreated() {
        //given
        BankAccountViewAModel createResponse =
                bankAccountManagementService.createBankAccount(BankAccountCreationModel.builder()
                        .balance(200L)
                        .build());
        SQLStatementCountValidator.reset();

        //when
        CashWithdrawalViewBModel cashWithdrawal = cashWithdrawalManagementService.createCashAction(
                CashWithdrawalCreationModel.builder()
                        .bankAccountId(createResponse.getId())
                        .cashAmount(100L)
                        .build());

        //then
        SQLStatementCountValidator.assertInsertCount(2);
        Assertions.assertEquals(4, QueryCountHolder.getGrandTotal().getTotal());

        Assertions.assertNotNull(cashWithdrawal);
        Assertions.assertNotNull(cashWithdrawal.getId());
        Assertions.assertEquals(100L, cashWithdrawal.getCashAmount());
        Assertions.assertEquals(1L, cashWithdrawal.getFee());
        BaseCashActionViewBModel.CashActionOperationModel operation = cashWithdrawal.getCashWithdrawalOperation();
        Assertions.assertNotNull(operation);
        Assertions.assertNotNull(operation.getId());
        Assertions.assertEquals(101L, operation.getAmount());
        Assertions.assertEquals(createResponse.getId(), operation.getBankAccountId());
    }

    @Test
    void givenCashWithdrawal_whenFindCashWithdrawalViewX_thenOnlyOneSqlExecuted() {
        //given
        BankAccountViewAModel bankAccount =
                bankAccountManagementService.createBankAccount(
                        BankAccountCreationModel.builder()
                                .balance(100L)
                                .build());
        CashWithdrawalViewBModel createResponse =
                cashWithdrawalManagementService.createCashAction(
                        CashWithdrawalCreationModel.builder()
                                .bankAccountId(bankAccount.getId())
                                .cashAmount(100L)
                                .build());
        SQLStatementCountValidator.reset();

        //when-then
        AssertUtils.assertSelectCountExactlyOne(() -> cashWithdrawalSearchService.findAll(CashWithdrawalViewAModel.class));
        AssertUtils.assertSelectCountExactlyOne(() -> cashWithdrawalSearchService.findAll(CashWithdrawalViewBModel.class));

        AssertUtils.assertSelectCountExactlyOne(() -> cashWithdrawalSearchService.findOneOrThrow(createResponse.getId(), CashWithdrawalViewAModel.class));
        AssertUtils.assertSelectCountExactlyOne(() -> cashWithdrawalSearchService.findOneOrThrow(createResponse.getId(), CashWithdrawalViewBModel.class));
    }

}
