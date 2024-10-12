package com.moneycat.budget.persistence.repository.impl;

import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.persistence.repository.custom.SpendingRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.QBudgetEntity;
import com.moneycat.budget.persistence.repository.entity.QCategoryEntity;
import com.moneycat.budget.persistence.repository.entity.QSpendingEntity;
import com.moneycat.budget.persistence.repository.entity.SpendingEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
    public List<SpendingEntity> selectMonthlySpendings(Long userId, LocalDate today) {
        return queryFactory.selectFrom(spendingEntity)
                .where(spendingEntity.userId.eq(userId)
                        .and(spendingEntity.date.year().eq(today.getYear()))
                        .and(spendingEntity.date.month().eq(today.getMonthValue()))
                        .and(spendingEntity.isExcluded.isFalse()))
                .fetch();
    }

    @Override
    public List<SpendingEntity> selectMonthlySpendingsExcludingToday(Long userId, LocalDate today) {
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

}
