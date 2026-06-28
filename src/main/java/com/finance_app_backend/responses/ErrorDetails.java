package com.finance_app_backend.responses;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDetails(
        LocalDateTime timestamp,
        int statusCode,
        String error,
        List<String> messages,
        String path
) {
}
