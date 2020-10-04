package com.siarhei.jpaefficiencyexperiments.cash.impl;

import com.siarhei.jpaefficiencyexperiments.cash.CashActionCreationService;
import com.siarhei.jpaefficiencyexperiments.cash.CashRefill;
import com.siarhei.jpaefficiencyexperiments.cash.CashRefillCreationModel;
import com.siarhei.jpaefficiencyexperiments.cash.CashRefillViewBModel;
import org.springframework.stereotype.Service;

@Service
public class CashRefillCreationServiceImpl
        extends AbstractCashActionCreationService<CashRefill, CashRefillViewBModel, CashRefillCreationModel>
        implements CashActionCreationService<CashRefillViewBModel, CashRefillCreationModel> {

    public CashRefillCreationServiceImpl(
            CashRefillMapper mapper,
            CashActionRepository cashActionRepository) {
        super(mapper::map, mapper::mapToViewB, cashActionRepository);
    }

}