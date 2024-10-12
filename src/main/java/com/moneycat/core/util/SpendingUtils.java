package com.moneycat.core.util;


import com.moneycat.core.constant.MoneyCatConstants;

import java.math.BigDecimal;

public class SpendingUtils {
    public static String chooseRecommendationMessage(BigDecimal totalSpent, BigDecimal totalBudget) {
        if (totalSpent.compareTo(totalBudget) > 0) {
            return MoneyCatConstants.OVER_BUDGET_MESSAGE;
        } else {
            return MoneyCatConstants.UNDER_BUDGET_MESSAGE;
        }
    }
}
