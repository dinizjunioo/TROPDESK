package br.com.checklistti.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
* Modelo de dados para um chamado de suporte.
* Mapeada para a tabela "tb_chamados" no banco de dados.
* @author Diniz Rodrigues
* @version 1.0.0
*/

// equivalente a @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@Data
// anotação do Swagger/OpenAPI para descrever a entidade Chamado 
@Entity
// anotação do JPA para mapear a classe Chamado para a tabela "tb_chamados" no banco de dados
@Builder
// anotação do Lombok para gerar um construtor com todos os campos obrigatórios (final ou @NonNull)
@NoArgsConstructor  // Exigido pelo JPA / Hibernate (cria o construtor padrão sem argumentos)
@AllArgsConstructor // Exigido pelo Lombok @Builder quando usado junto com @NoArgsConstruct
@Table(name = "tb_chamados")
@Schema(description = "Entidade que representa um ticket/chamado de suporte no sistema")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do chamado", example = "101", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(length = 50, nullable = false)
    @Schema(description = "Título do chamado", example = "Problema com a impressora", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @Column(length = 200, nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Descrição do problema", example = "A impressora não está imprimindo corretamente", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descricaoProblema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Status do chamado", example = "ABERTO", requiredMode = Schema.RequiredMode.REQUIRED)
    @Builder.Default
    private StatusChamado status = StatusChamado.ABERTO;

    
    @Column(nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do chamado", example = "2024-06-01T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();

    
    @Column(nullable = false)
    @Schema(description = "Data e hora de atualização do chamado", example = "2024-06-01T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    @Builder.Default
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    @Schema(description = "Técnico responsável pelo chamado", accessMode = Schema.AccessMode.READ_ONLY)
    private Tecnico tecnico;

    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de itens do checklist associados ao chamado", accessMode = Schema.AccessMode.READ_ONLY)
    @Builder.Default
    private List<ItemChecklist> itensChecklist = new ArrayList<>();

    // Getter defensivo (opcional, mas recomendado):
    public List<ItemChecklist> getChecklist() {
        if (this.itensChecklist == null) {
            this.itensChecklist = new ArrayList<>();
        }
        return this.itensChecklist;
    }
    /**
     * Executado automaticamente pelo JPA ANTES de salvar uma NOVA entidade (INSERT).
     */
    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Executado automaticamente pelo JPA ANTES de atualizar uma entidade EXISTENTE (UPDATE).
     */
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // /* builder */
    // private Chamado(Long id, String titulo, String descricaoProblema, StatusChamado status, 
    //     Tecnico tecnico, List<ItemChecklist> itensChecklist, 
    //     LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) 
    // {
    //     this.id = id;
    //     this.titulo = titulo;
    //     this.descricaoProblema = descricaoProblema;
    //     this.status = status;
    //     this.tecnico = tecnico;
    //     this.itensChecklist = itensChecklist;
    //     this.dataCriacao = dataCriacao;
    //     this.dataAtualizacao = dataAtualizacao;
    // }

    
    // public static class ChamadoBuilder{

    //     private Long id;
    //     private String titulo;
    //     private String descricaoProblema;
    //     private StatusChamado status;
    //     private Tecnico tecnico;
    //     private List<ItemChecklist> itensChecklist;
    //     private LocalDateTime dataCriacao;
    //     private LocalDateTime dataAtualizacao;
        
    //     public ChamadoBuilder id(Long id) {
    //         this.id = id;
    //         return this;
    //     }
    //     public ChamadoBuilder titulo(String titulo) {
    //         this.titulo = titulo;
    //         return this;
    //     }
    //     public ChamadoBuilder descricaoProblema(String descricaoProblema) {
    //         this.descricaoProblema = descricaoProblema;
    //         return this;
    //     }
    //     public ChamadoBuilder status(StatusChamado status) {
    //         this.status = status;
    //         return this;
    //     }
    //     public ChamadoBuilder tecnico(Tecnico tecnico) {
    //         this.tecnico = tecnico;
    //         return this;
    //     }
    //     public ChamadoBuilder itensChecklist(List<ItemChecklist> itensChecklist) {
    //         this.itensChecklist = itensChecklist;
    //         return this;
    //     }
    //     public ChamadoBuilder dataCriacao(LocalDateTime dataCriacao) {
    //         this.dataCriacao = dataCriacao;
    //         return this;
    //     }
    //     public ChamadoBuilder dataAtualizacao(LocalDateTime dataAtualizacao) {
    //         this.dataAtualizacao = dataAtualizacao;
    //         return this;
    //     }
    //     public Chamado build() {
    //         return new Chamado(id, titulo, descricaoProblema, status, 
    //             tecnico, itensChecklist, dataCriacao, dataAtualizacao);
    //     }
    // }

    
}
