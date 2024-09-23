package com.moneycat.budget.persistence.repository.entity;

import com.moneycat.budget.controller.model.request.SpendingRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "spending")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingEntity  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "memo", nullable = false)
    private String memo;

    @Column(name = "is_excluded", nullable = false)
    private boolean isExcluded;

    public void update(SpendingRequest spendingRequest) {
        this.categoryId = spendingRequest.categoryId();
        this.amount = spendingRequest.amount();
        this.date = spendingRequest.date();
        this.memo = spendingRequest.memo();
        this.isExcluded = spendingRequest.isExcluded();
    }
}
