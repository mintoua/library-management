package com.syniiq.library_management.application.dto.Livre;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record LivreDTO(
        Long id,
        @NotBlank(message = "Titre is required")
        String titre,
        String description,
        @NotBlank(message = "ISBN is required")
        String isbn,
        @NotEmpty(message = "At least one author is required")
        Set<Long> auteurIds,
        boolean disponible
) {
}
