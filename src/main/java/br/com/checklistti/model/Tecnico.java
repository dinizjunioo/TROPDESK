package br.com.checklistti.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tecnicos")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    private RoleTecnico role = RoleTecnico.TECNICO;
}
