package br.com.checklistti.controller;

import br.com.checklistti.model.ItemChecklist;
import br.com.checklistti.repository.ChamadoRepository;
import br.com.checklistti.repository.ItemChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChecklistController {

    private final ItemChecklistRepository itemRepository;
    private final ChamadoRepository chamadoRepository;

    // GET /api/chamados/{id}/checklist — listar itens
    @GetMapping("/api/chamados/{id}/checklist")
    public ResponseEntity<List<ItemChecklist>> listar(@PathVariable Long id) {
        return ResponseEntity.ok(itemRepository.findByChamadoId(id));
    }

    // POST /api/chamados/{id}/checklist — adicionar item
    @PostMapping("/api/chamados/{id}/checklist")
    public ResponseEntity<ItemChecklist> adicionar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return chamadoRepository.findById(id).map(chamado -> {
            ItemChecklist item = new ItemChecklist();
            item.setDescricao(body.get("descricao"));
            item.setChamado(chamado);
            return ResponseEntity.status(201).body(itemRepository.save(item));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/checklist/{id} — marcar como feito
    @PatchMapping("/api/checklist/{id}")
    public ResponseEntity<ItemChecklist> marcarFeito(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        return itemRepository.findById(id).map(item -> {
            item.setConcluido(body.get("concluido"));
            return ResponseEntity.ok(itemRepository.save(item));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/checklist/{id}
    @DeleteMapping("/api/checklist/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) return ResponseEntity.notFound().build();
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
