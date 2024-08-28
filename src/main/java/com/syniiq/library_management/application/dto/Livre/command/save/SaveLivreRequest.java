package com.syniiq.library_management.application.dto.Livre.command.save;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Data
public class SaveLivreRequest {
    @NotBlank(message = "Titre is required")
    private String titre;

    private String description;

    @NotNull(message = "isbn is required")
    private String isbn;

    @NotEmpty(message = "At least one author is required")
    Set<Long> auteurIds;
    boolean disponible;
}
