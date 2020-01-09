package com.siarhei.jpatransactionaldemo.moneytransfer.management;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransferCreationModel {

    private Long fromBankAccountId;
    private Long toBankAccountId;
    private Long amount;

}
