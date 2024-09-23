package com.moneycat.budget.persistence.repository.impl;

import com.moneycat.budget.persistence.repository.custom.BudgetRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.QBudgetEntity;
import com.moneycat.budget.service.BudgetCategoryPercentageDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.moneycat.budget.persistence.repository.entity.QBudgetEntity.*;

@Repository
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BudgetCategoryPercentageDto> findUserBudgetByMonth(LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(BudgetCategoryPercentageDto.class
                        , budgetEntity.userId
                        , budgetEntity.categoryId
                        , budgetEntity.amount
                ))
                .from(budgetEntity)
                .where(budgetEntity.startDate.goe(startDate).and(budgetEntity.endDate.loe(endDate)))
                .fetch();
    }
}
