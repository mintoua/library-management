package com.syniiq.library_management.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb-user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String email;
    private Boolean isActivated;
    private Boolean isBlocked;
    @OneToMany(mappedBy = "utilisateur")
    private Set<Emprunt> emprunts = new HashSet<>();
    @Column(name = "date-ajout")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "date-modification")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Column(name = "supprimer")
    private Boolean isDeleted;
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
}
