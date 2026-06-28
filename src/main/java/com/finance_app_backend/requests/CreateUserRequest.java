package com.finance_app_backend.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @Size(min = 4, max = 32, message = "Username must be between 4 and 32 characters")
        String username,

        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters long")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 32, message = "Last name must be between 4 and 32 characters long")
        @NotBlank(message = "Last name is required") String lastName
) {
}
