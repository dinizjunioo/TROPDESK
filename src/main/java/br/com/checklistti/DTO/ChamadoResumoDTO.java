package br.com.checklistti.DTO;


import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;

import lombok.Getter;

import java.util.Optional;

import java.time.LocalDateTime;

@Getter
public class ChamadoResumoDTO {
    private Long id;
    private String titulo;
    private String descricaoProblema;
    private StatusChamado status;
    private LocalDateTime dataCriacao;
    private String tecnico;
    private int totalItensChecklist;

    public ChamadoResumoDTO(Chamado chamado) {
        this.id = chamado.getId();
        this.titulo = chamado.getTitulo();
        this.descricaoProblema = chamado.getDescricaoProblema();
        this.status = chamado.getStatus();
        this.dataCriacao = chamado.getDataCriacao();

        // 🛡️ Prevenindo NPE no Técnico usando Optional
        this.tecnico = Optional.ofNullable(chamado.getTecnico())
                .map(tecnico -> tecnico.getNome())
                //.map(Tecnico::getNome)
                .orElse("Não atribuído");

        // 🛡️ Prevenindo NPE na Lista usando Optional
        this.totalItensChecklist = Optional.ofNullable(chamado.getChecklist())
                .map(list -> chamado.getItensChecklist().size())
                //.map(List::size)
                .orElse(0);
    }
}
