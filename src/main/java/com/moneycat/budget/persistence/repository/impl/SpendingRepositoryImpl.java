package com.moneycat.budget.persistence.repository.impl;

import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.persistence.repository.custom.SpendingRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.moneycat.budget.service.dto.FinanceDto;
import com.moneycat.budget.service.dto.BudgetSpendingDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.moneycat.budget.persistence.repository.entity.QBudgetEntity.*;
import static com.moneycat.budget.persistence.repository.entity.QCategoryEntity.*;
import static com.moneycat.budget.persistence.repository.entity.QSpendingEntity.*;


@Repository
@RequiredArgsConstructor
public class SpendingRepositoryImpl implements SpendingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SpendingDetailResponse> selectAllSpending(Long userId, SpendingSearchRequest searchRequest) {
        return queryFactory.select(Projections.constructor(
                        SpendingDetailResponse.class,
                        spendingEntity.id,
                        spendingEntity.categoryId,
                        categoryEntity.name,
                        spendingEntity.date,
                        spendingEntity.amount,
                        spendingEntity.isExcluded,
                        spendingEntity.memo,
                        spendingEntity.createdAt,
                        spendingEntity.updatedAt
                ))
                .from(spendingEntity)
                .join(categoryEntity).on(spendingEntity.categoryId.eq(categoryEntity.id))
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.between(searchRequest.startDate(), searchRequest.endDate())))
                .orderBy(spendingEntity.date.desc())
                .fetch();
    }

    @Override
    public List<SpendingEntity> selectMonthlySpending(Long userId, LocalDate today) {
        return queryFactory.selectFrom(spendingEntity)
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.year().eq(today.getYear()))
                        .and(spendingEntity.date.month().eq(today.getMonthValue()))
                        .and(spendingEntity.isExcluded.isFalse()))
                .fetch();
    }

    @Override
    public List<SpendingEntity> selectMonthlySpendingExcludingToday(Long userId, LocalDate today) {
        return queryFactory.selectFrom(spendingEntity)
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.year().eq(today.getYear()))
                        .and(spendingEntity.date.month().eq(today.getMonthValue()))
                        .and(spendingEntity.date.ne(today))
                        .and(spendingEntity.isExcluded.isFalse()))
                .fetch();
    }

    @Override
    public List<SpendingEntity> selectAllSpendingToday(Long userId, LocalDate today) {
        return queryFactory.selectFrom(spendingEntity)
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.eq(today))
                        .and(spendingEntity.isExcluded.isFalse()))
                .fetch();
    }

    @Override
    public List<FinanceDto> selectSpendingForPeriod(Long userId, LocalDate startDate, LocalDate endDate) {
        return queryFactory
                .select(Projections.constructor(FinanceDto.class
                        ,spendingEntity.categoryId
                        ,categoryEntity.name
                        ,spendingEntity.amount.sum()
                ))
                .from(spendingEntity)
                .join(categoryEntity)
                .on(spendingEntity.categoryId.eq(categoryEntity.id))
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.between(startDate, endDate))
                        .and(spendingEntity.isExcluded.isFalse()))
                .groupBy(spendingEntity.categoryId)
                .fetch();
    }

    @Override
    public BigDecimal getTotalSpent(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal result = queryFactory
                .select(spendingEntity.amount.sum())
                .from(spendingEntity)
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.between(startDate, endDate))
                        .and(spendingEntity.isExcluded.isFalse()))
                .fetchOne();

        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public List<BudgetSpendingDto> selectOtherUsersBudgetSpending(Long excludedUserId, LocalDate today) {
        return queryFactory
                .select(Projections.constructor(
                        BudgetSpendingDto.class,
                        spendingEntity.userId,
                        spendingEntity.amount.sum(),
                        budgetEntity.amount.sum()
                ))
                .from(spendingEntity)
                .join(budgetEntity).on(spendingEntity.userId.eq(budgetEntity.userId))
                .where(spendingEntity.userId.ne(excludedUserId)
                        .and(spendingEntity.date.year().eq(today.getYear()))
                        .and(spendingEntity.date.month().eq(today.getMonthValue())))
                .groupBy(spendingEntity.userId)
                .fetch();
    }

}
