package com.moneycat.budget.persistence.repository.impl;

import com.moneycat.budget.persistence.repository.custom.BudgetRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.BudgetEntity;
import com.moneycat.budget.persistence.repository.entity.QBudgetEntity;
import com.moneycat.budget.persistence.repository.entity.QCategoryEntity;
import com.moneycat.budget.service.BudgetCategoryPercentageDto;
import com.moneycat.budget.service.dto.MonthlyBudgetDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.moneycat.budget.persistence.repository.entity.QBudgetEntity.*;
import static com.moneycat.budget.persistence.repository.entity.QCategoryEntity.*;

@Repository
@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BudgetCategoryPercentageDto> findOtherUsersBudgetByMonth(Long excludedUserId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(BudgetCategoryPercentageDto.class
                        , budgetEntity.userId
                        , budgetEntity.categoryId
                        , budgetEntity.amount
                ))
                .from(budgetEntity)
                .where(budgetEntity.startDate.goe(startDate)
                        .and(budgetEntity.endDate.loe(endDate))
                        .and(budgetEntity.amount.ne(BigDecimal.ZERO))
                        .and(budgetEntity.userId.ne(excludedUserId)))
                .fetch();
    }

    @Override
    public List<MonthlyBudgetDto> selectMonthlyBudgets(Long userId, LocalDate today) {
        return queryFactory
                .select(Projections.constructor(
                        MonthlyBudgetDto.class,
                        budgetEntity.categoryId,
                        categoryEntity.name,
                        budgetEntity.amount
                ))
                .from(budgetEntity)
                .join(categoryEntity).on(budgetEntity.categoryId.eq(categoryEntity.id))
                .where(budgetEntity.userId.eq(userId)
                        .and(budgetEntity.startDate.year().eq(today.getYear()))
                        .and(budgetEntity.startDate.month().eq(today.getMonthValue())))
                .fetch();
    }

    @Override
    public BigDecimal getBudgets(Long userId, LocalDate today) {
        return queryFactory
                .select(budgetEntity.amount.sum())
                .from(budgetEntity)
                .where(budgetEntity.userId.eq(userId)
                        .and(budgetEntity.startDate.year().eq(today.getYear()))
                        .and(budgetEntity.startDate.month().eq(today.getMonthValue())))
                .fetchOne();
    }
}
