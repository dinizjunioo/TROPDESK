package br.com.checklistti.service;


//import br.com.checklistti.dto.ChamadoInputDTO;
import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;
import br.com.checklistti.model.Tecnico;
import br.com.checklistti.dto.ChamadoDetalhadoDTO;
import br.com.checklistti.repository.ChamadoRepository;
import br.com.checklistti.repository.TecnicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final TecnicoRepository tecnicoRepository;

    // Injeção de dependências por construtor (Boa prática do Spring)
    public ChamadoService(ChamadoRepository chamadoRepository, TecnicoRepository tecnicoRepository) {
        this.chamadoRepository = chamadoRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    /**
     * Cria e salva um novo chamado no sistema.
     */
    // @Transactional
    // public ChamadoDetalhadoDTO criar(ChamadoInputDTO dto) {
    //     // Usa o @Builder do Lombok para criar a entidade
    //     Chamado.ChamadoBuilder chamadoBuilder = Chamado.builder()
    //             .titulo(dto.titulo())
    //             .descricaoProblema(dto.descricaoProblema())
    //             .status(StatusChamado.ABERTO); // Garantindo status inicial

    //     // Vincula o técnico caso tenha sido enviado no DTO
    //     if (dto.tecnicoId() != null) {
    //         Tecnico tecnico = tecnicoRepository.findById(dto.tecnicoId())
    //                 .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado com ID: " + dto.tecnicoId()));
    //         chamadoBuilder.tecnico(tecnico);
    //     }

    //     Chamado novoChamado = chamadoBuilder.build();
        
    //     // O @PrePersist da sua entidade cuidará de preencher a dataCriacao e dataAtualizacao
    //     Chamado chamadoSalvo = chamadoRepository.save(novoChamado);

    //     return new ChamadoDetalhadoDTO(chamadoSalvo);
    // }

    /**
     * Retorna uma lista paginada de chamados.
     */
    @Transactional(readOnly = true)
    public Page<ChamadoDetalhadoDTO> listarTodos(Pageable pageable) {
        return chamadoRepository.findAll(pageable)
                .map(ChamadoDetalhadoDTO::new);
    }

    /**
     * Busca um chamado pelo ID.
     */
    @Transactional(readOnly = true)
    public ChamadoDetalhadoDTO buscarPorId(Long id) {
        Chamado chamado = buscarEntidadePorId(id);
        return new ChamadoDetalhadoDTO(chamado);
    }

    /**
     * Atribui um técnico a um chamado existente.
     */
    @Transactional
    public ChamadoDetalhadoDTO atribuirTecnico(Long chamadoId, Long tecnicoId) {
        Chamado chamado = buscarEntidadePorId(chamadoId);
        
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new EntityNotFoundException("Técnico não encontrado com ID: " + tecnicoId));

        chamado.setTecnico(tecnico);
        // O JPA gerencia a atualização e o @PreUpdate atualizará a dataAtualizacao automaticamente
        Chamado chamadoAtualizado = chamadoRepository.save(chamado);

        return new ChamadoDetalhadoDTO(chamadoAtualizado);
    }

    /**
     * Atualiza o status de um chamado.
     */
    @Transactional
    public ChamadoDetalhadoDTO alterarStatus(Long id, StatusChamado novoStatus) {
        Chamado chamado = buscarEntidadePorId(id);
        chamado.setStatus(novoStatus);
        
        Chamado chamadoAtualizado = chamadoRepository.save(chamado);
        return new ChamadoDetalhadoDTO(chamadoAtualizado);
    }

    /**
     * Método utilitário privado para reuso da busca por ID com tratamento de erro.
     */
    private Chamado buscarEntidadePorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chamado não encontrado com o ID: " + id));
    }
}
