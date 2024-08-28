package com.syniiq.library_management.domain.service;

import com.syniiq.library_management.application.dto.Livre.LivreDTO;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreRequest;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreResponse;

import java.util.List;

public interface LivreService {
    List<LivreDTO> getLivres();
    SaveLivreResponse saveLivre(SaveLivreRequest request);
    LivreDTO getLivreById(Long id);
    SaveLivreResponse updateLivre(Long id, SaveLivreRequest request);
    SaveLivreResponse deleteLivre(Long id);

}
