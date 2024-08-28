package com.syniiq.library_management.application.dto.Livre.command.save;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveLivreResponse {
    private boolean isSaved;
    private String message;
    private Long livreId;

    public SaveLivreResponse() {
        this.livreId = null;
        this.isSaved = false;
        this.message = "";
    }
}
