package br.com.checklistti.controller;

import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;
import br.com.checklistti.repository.ChamadoRepository;
import br.com.checklistti.repository.TecnicoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoRepository chamadoRepository;
    private final TecnicoRepository tecnicoRepository;

    // GET /api/chamados — listar todos (com paginação)
    @GetMapping
    public Page<Chamado> listar(Pageable pageable) {
        return chamadoRepository.findAll(pageable);
    }

    // GET /api/chamados/{id} — buscar um chamado
    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscar(@PathVariable Long id) {
        return chamadoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/chamados — abrir chamado
    @PostMapping
    public ResponseEntity<Chamado> criar(@RequestBody @Valid Chamado chamado) {
        Chamado salvo = chamadoRepository.save(chamado);
        return ResponseEntity.status(201).body(salvo);
    }

    // PATCH /api/chamados/{id}/status — atualizar status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Chamado> atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return chamadoRepository.findById(id).map(chamado -> {
            StatusChamado novoStatus = StatusChamado.valueOf(body.get("status"));
            chamado.setStatus(novoStatus);
            return ResponseEntity.ok(chamadoRepository.save(chamado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/chamados/{id}/ferramentas — registrar local da ferramenta
    @PatchMapping("/{id}/ferramentas")
    public ResponseEntity<Chamado> registrarFerramentas(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return chamadoRepository.findById(id).map(chamado -> {
            chamado.setLocalFerramentas(body.get("localFerramentas"));
            chamado.setStatus(StatusChamado.FERRAMENTA_NO_LOCAL);
            return ResponseEntity.ok(chamadoRepository.save(chamado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/chamados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!chamadoRepository.existsById(id)) return ResponseEntity.notFound().build();
        chamadoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
