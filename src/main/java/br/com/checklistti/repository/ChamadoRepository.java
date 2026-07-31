package br.com.checklistti.repository;

import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);
    Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable);


    // GET /usuarios?page=0&size=10&sort=nome,asc
    // mesma coisa
    // Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
}
