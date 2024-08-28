package com.syniiq.library_management.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb-emprunt")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Emprunt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private User utilisateur;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @Column(name = "date-emprunt")
    @Temporal(TemporalType.DATE)
    private Date dateEmprunt;

    @Column(name = "date-retour")
    @Temporal(TemporalType.DATE)
    private Date dateRetour;
    @Column(name = "date-ajout")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "date-modification")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Column(name = "supprimer")
    private Boolean isDeleted;
}
