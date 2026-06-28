package com.finance_app_backend.user;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String username,
        String firstName,
        String lastName,
        String role
) {
}
