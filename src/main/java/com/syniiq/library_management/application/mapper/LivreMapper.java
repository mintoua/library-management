package com.syniiq.library_management.application.mapper;

import com.syniiq.library_management.application.dto.Livre.LivreDTO;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreRequest;
import com.syniiq.library_management.domain.model.Auteur;
import com.syniiq.library_management.domain.model.Livre;

import java.util.stream.Collectors;

public class LivreMapper {
    public static LivreDTO toDTO(Livre livre) {
        return new LivreDTO(
                livre.getId(),
                livre.getTitre(),
                livre.getDescription(),
                livre.getIsbn(),
                livre.getAuteurs().stream()
                        .map(Auteur::getId)
                        .collect(Collectors.toSet()),
                livre.isDisponible()
        );
    }

    public static Livre toEntity(LivreDTO dto) {
        Livre livre = new Livre();
        livre.setId(dto.id());
        livre.setTitre(dto.titre());
        livre.setDescription(dto.description());
        livre.setIsbn(dto.isbn());
        livre.setDisponible(dto.disponible());
        return livre;
    }
    public static Livre toEntity(SaveLivreRequest dto) {
        Livre livre = new Livre();
        livre.setTitre(dto.getTitre());
        livre.setDescription(dto.getDescription());
        livre.setIsbn(dto.getIsbn());
        livre.setDisponible(dto.isDisponible());
        return livre;
    }
}
