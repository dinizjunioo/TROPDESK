package br.com.checklistti.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "itens_checklist")
public class ItemChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    private boolean concluido = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;
}
