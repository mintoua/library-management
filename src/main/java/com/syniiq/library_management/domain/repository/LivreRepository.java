package com.syniiq.library_management.domain.repository;


import com.syniiq.library_management.domain.model.Auteur;
import com.syniiq.library_management.domain.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivreRepository  {
    Livre save(Livre book);
    Optional<Livre> findById(Long id);
    List<Livre> findAll();
    void deleteById(Long id);
    List<Livre> findByAuthorId(Long authorId);
    List<Livre> findAvailable();
    List<Livre> findByAuthor(Auteur author);
}
