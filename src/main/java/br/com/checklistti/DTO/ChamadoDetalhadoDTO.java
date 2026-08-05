package br.com.checklistti.dto;

import java.time.LocalDateTime;

import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.ItemChecklist;
import br.com.checklistti.model.StatusChamado;
import lombok.Getter;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ChamadoDetalhadoDTO {
    
    private Long id;
    private String titulo;
    private String descricaoProblema;
    private StatusChamado status;
    private LocalDateTime dataCriacao;
    private String tecnico;
    private List<ItemChecklist> itemchecklist = new ArrayList<>();


    public ChamadoDetalhadoDTO(Chamado chamado) {
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
        this.itemchecklist = chamado.getChecklist();
                
    }

}
