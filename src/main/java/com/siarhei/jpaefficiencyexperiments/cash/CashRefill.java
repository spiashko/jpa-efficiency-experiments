package com.siarhei.jpaefficiencyexperiments.cash;

import com.siarhei.jpaefficiencyexperiments.operation.CashRefillOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("REFILL")
public class CashRefill extends CashAction {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "fk_refill_operation", updatable = false)
    private CashRefillOperation cashRefillOperation;

    public void setCashRefillOperation(CashRefillOperation cashRefillOperation) {
        cashRefillOperation.setCashRefill(this);
        this.cashRefillOperation = cashRefillOperation;
    }
}
