package com.finance_app_backend.transaction;

import java.math.BigDecimal;

public record SummaryDTO(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance
) {
}
