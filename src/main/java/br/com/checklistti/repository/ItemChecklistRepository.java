package br.com.checklistti.repository;

import br.com.checklistti.model.ItemChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemChecklistRepository extends JpaRepository<ItemChecklist, Long> {
    List<ItemChecklist> findByChamadoId(Long chamadoId);
}
