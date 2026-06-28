package com.finance_app_backend.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDTO(
        UUID id,
        BigDecimal amount,
        LocalDate date,
        String description,
        TransactionType type
) {
}
