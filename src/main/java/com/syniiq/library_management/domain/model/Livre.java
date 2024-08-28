package com.syniiq.library_management.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb-livre")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Livre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titre;
    @NotBlank
    @Column(unique = true)
    private String isbn;
    private String description;
    private  boolean isDisponible;
    @Column(name = "date-ajout")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "date-modification")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Column(name = "supprimer")
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "livre_auteur",
            joinColumns = @JoinColumn(name = "livre_id"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private Set<Auteur> auteurs = new HashSet<>();
}
