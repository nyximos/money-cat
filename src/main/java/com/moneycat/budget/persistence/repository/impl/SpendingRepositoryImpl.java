package com.moneycat.budget.persistence.repository.impl;

import com.moneycat.budget.controller.model.request.SpendingSearchRequest;
import com.moneycat.budget.controller.model.response.SpendingDetailResponse;
import com.moneycat.budget.persistence.repository.custom.SpendingRepositoryCustom;
import com.moneycat.budget.persistence.repository.entity.QCategoryEntity;
import com.moneycat.budget.persistence.repository.entity.QSpendingEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
