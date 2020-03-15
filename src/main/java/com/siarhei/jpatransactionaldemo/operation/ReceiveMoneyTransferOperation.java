package com.siarhei.jpatransactionaldemo.operation;

import com.siarhei.jpatransactionaldemo.bankaccount.BankAccount;
import com.siarhei.jpatransactionaldemo.moneytransfer.MoneyTransfer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("RECEIVE_MONEY_TRANSFER")
public class ReceiveMoneyTransferOperation extends InOperation {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "receiveOperation", optional = false)
    private MoneyTransfer receiveMoneyTransfer;

    @Builder
    public ReceiveMoneyTransferOperation(BankAccount bankAccount, @NotNull Long amount, MoneyTransfer receiveMoneyTransfer) {
        super(bankAccount, amount);
        this.receiveMoneyTransfer = receiveMoneyTransfer;
    }

}
