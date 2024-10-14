package com.moneycat.core.util;


import com.moneycat.core.constant.MoneyCatConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpendingUtils {
    public static String chooseRecommendationMessage(BigDecimal totalSpent, BigDecimal totalBudget) {
        if (totalSpent.compareTo(totalBudget) > 0) {
            return MoneyCatConstants.OVER_BUDGET_MESSAGE;
        } else {
            return MoneyCatConstants.UNDER_BUDGET_MESSAGE;
        }
    }

    public static BigDecimal calculateRate(BigDecimal lastMonthAmount, BigDecimal currentAmount) {
        if (lastMonthAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.valueOf(100);
        }
        return currentAmount.divide(lastMonthAmount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
