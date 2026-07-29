package br.com.checklistti.repository;

import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);
    Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable);
}
