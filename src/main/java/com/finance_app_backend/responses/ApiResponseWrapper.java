package com.finance_app_backend.responses;

import java.time.LocalDateTime;

public record ApiResponseWrapper<T>(
        LocalDateTime timestamp,
        int statusCode,
        String message,
        T data
) {
}
