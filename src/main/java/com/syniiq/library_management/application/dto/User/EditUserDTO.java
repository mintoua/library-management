package com.syniiq.library_management.application.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EditUserDTO(
        @Email(message = "enter valid email address")
        String email,
        @NotEmpty(message = "field couldn't be empty")
        String firstname,
        String lastname) {
}
