package com.syniiq.library_management.application.service;

import com.syniiq.library_management.application.dto.Livre.LivreDTO;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreRequest;
import com.syniiq.library_management.application.dto.Livre.command.save.SaveLivreResponse;
import com.syniiq.library_management.application.mapper.LivreMapper;
import com.syniiq.library_management.domain.model.Livre;
import com.syniiq.library_management.domain.repository.LivreRepository;
import com.syniiq.library_management.domain.service.LivreService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivreServiceImpl implements LivreService {

    private final LivreRepository livreRepository;

    public LivreServiceImpl(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    @Override
    public List<LivreDTO> getLivres() {
        return livreRepository.findAll().stream()
                .filter(livre -> !livre.getIsDeleted())
                .map(LivreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaveLivreResponse saveLivre(SaveLivreRequest request) {
        SaveLivreResponse response = new SaveLivreResponse();
        try {
            Livre livre = LivreMapper.toEntity(request);
            livre.setCreatedAt(new Date());
            livre.setUpdatedAt(new Date());
            livre.setIsDeleted(false);
            livreRepository.save(livre);
            response.setSaved(true);
            response.setMessage("Livre saved successfully.");
            response.setLivreId(livre.getId());
        }catch (Exception e) {
            response.setMessage("Error saving livre: " + e.getMessage());
        }
        return response;
    }

    @Override
    public LivreDTO getLivreById(Long id) {
        return livreRepository.findById(id)
                .map(LivreMapper::toDTO)
                .orElse(null);
    }

    @Override
    public SaveLivreResponse updateLivre(Long id, SaveLivreRequest request) {
        SaveLivreResponse response = new SaveLivreResponse();
        try {
            Livre livre = livreRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Livre not found."));
            livre.setCreatedAt(new Date());
            livre.setUpdatedAt(new Date());
            livre.setIsDeleted(false);
            livreRepository.save(livre);
            response.setSaved(true);
            response.setMessage("LIvre updated successfully.");
            response.setLivreId(livre.getId());
        }catch (Exception e) {
            response.setMessage("Error updating: " + e.getMessage());
        }
        return response;
    }

    @Override
    public SaveLivreResponse deleteLivre(Long id) {
        SaveLivreResponse response = new SaveLivreResponse();
        try {
            Livre livre = livreRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Livre not found."));
            livre.setIsDeleted(true);
            livre.setUpdatedAt(new Date());
            livreRepository.save(livre);
            response.setSaved(true);
            response.setMessage("Livre deleted successfully.");
        } catch (Exception e) {
            response.setMessage("Error deleting livre: " + e.getMessage());
        }
        return response;
    }
}
