package com.finance_app_backend.requests;

import com.finance_app_backend.transaction.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(
        @NotNull
        @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
        BigDecimal amount,

        @NotNull(message = "Date is required")
        LocalDate date,

        @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters")
        String description,

        @NotNull(message = "Type is required")
        TransactionType type
) {
}
