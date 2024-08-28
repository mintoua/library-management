package com.syniiq.library_management.infrastructure.persistence;

import com.syniiq.library_management.domain.model.Auteur;
import com.syniiq.library_management.domain.model.Livre;
import com.syniiq.library_management.domain.repository.LivreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LivreRepositoryImpl implements LivreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Livre save(Livre book) {
        if (book.getId() == null) {
            entityManager.persist(book);
        } else {
            book = entityManager.merge(book);
        }
        return book;
    }

    @Override
    public Optional<Livre> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Livre.class, id));
    }

    @Override
    public List<Livre> findAll() {
        TypedQuery<Livre> query = entityManager.createQuery("SELECT b FROM Livre b", Livre.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Livre book = entityManager.find(Livre.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

    @Override
    public List<Livre> findByAuthorId(Long authorId) {
        return List.of();
    }

    @Override
    public List<Livre> findAvailable() {
        TypedQuery<Livre> query = entityManager.createQuery(
                "SELECT b FROM Livre b WHERE b.isDisponible = true", Livre.class);
        return query.getResultList();
    }

    @Override
    public List<Livre> findByAuthor(Auteur author) {
        TypedQuery<Livre> query = entityManager.createQuery(
                "SELECT b FROM Livre b JOIN b.auteurs a WHERE a = :author", Livre.class);
        query.setParameter("author", author);
        return query.getResultList();
    }
}
