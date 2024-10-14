package com.moneycat.core.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SpendingUtilsTest {
    @Test
    void 지난달_소비가_0일때_결과는_100() {
        // Given
        BigDecimal lastMonthAmount = BigDecimal.ZERO;
        BigDecimal currentMonthAmount = BigDecimal.valueOf(120000);

        // When
        BigDecimal result = SpendingUtils.calculateRate(lastMonthAmount, currentMonthAmount);

        // Then
        assertEquals(BigDecimal.valueOf(100), result, "지난달 소비가 0일 때, 결과는 100%여야 합니다.");
    }

    @Test
    void 지난달_소비가_더_적을때_소비율_계산() {
        // Given
        BigDecimal lastMonthAmount = BigDecimal.valueOf(100000);
        BigDecimal currentMonthAmount = BigDecimal.valueOf(120000);

        // When
        BigDecimal result = SpendingUtils.calculateRate(lastMonthAmount, currentMonthAmount);

        // Then
        assertTrue(result.compareTo(BigDecimal.valueOf(120.00))==0, "지난달 소비보다 20% 증가한 값이 나와야 합니다.");
    }

    @Test
    void 지난달_소비가_현재_소비보다_많을때_소비율_계산() {
        // Given
        BigDecimal lastMonthAmount = BigDecimal.valueOf(150000);
        BigDecimal currentMonthAmount = BigDecimal.valueOf(100000);

        // When
        BigDecimal result = SpendingUtils.calculateRate(lastMonthAmount, currentMonthAmount);

        // Then
        assertTrue(result.compareTo(BigDecimal.valueOf(67.00))==0, "지난달 소비보다 33.33% 감소한 값이 나와야 합니다.");
    }

    @Test
    void 지난달과_소비가_같을때_결과는_100() {
        // Given
        BigDecimal lastMonthAmount = BigDecimal.valueOf(100000);
        BigDecimal currentMonthAmount = BigDecimal.valueOf(100000);

        // When
        BigDecimal result = SpendingUtils.calculateRate(lastMonthAmount, currentMonthAmount);

        // Then
        assertTrue(result.compareTo(BigDecimal.valueOf(100))==0 , "지난달 소비와 같은 경우 100%여야 합니다.");
    }
}