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
@NamedEntityGraph(
        name = "CashRefill.all",
        attributeNodes = {
                @NamedAttributeNode(value = "cashRefillOperation", subgraph = "Operation.bankAccount"),
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "Operation.bankAccount",
                        attributeNodes = @NamedAttributeNode("bankAccount")
                )
        }
)
public class CashRefill extends CashAction<CashRefillOperation> {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "fk_refill_operation", updatable = false)
    private CashRefillOperation cashRefillOperation;

    @Override
    public CashRefillOperation getCashOperation() {
        return this.cashRefillOperation;
    }
}
