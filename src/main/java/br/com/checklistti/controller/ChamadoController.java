package br.com.checklistti.controller;

//import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import br.com.checklistti.dto.ChamadoDetalhadoDTO;
import br.com.checklistti.dto.ChamadoResumoDTO;
import br.com.checklistti.model.Chamado;
import br.com.checklistti.repository.ChamadoRepository;
//import br.com.checklistti.repository.TecnicoRepository;
import jakarta.validation.Valid;

import org.springframework.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//import java.util.Map;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor

/*
 * ============================================================================================
 * 📌 INJEÇÃO DE DEPENDÊNCIA VIA CONSTRUTOR COM LOMBOK E SPRING
 * ============================================================================================
 * 0. A classe ChamadoController depende de ChamadoRepository, ou seja da sua instância, para realizar operações de CRUD.
 *
 * 1. POR QUE USAR @RequiredArgsConstructor?
 *    Esta anotação do Lombok gera automaticamente um construtor APENAS para os campos 
 *    obrigatórios da classe — ou seja, atributos declarados como 'final' ou marcados com @NonNull.
 *
 * 2. POR QUE O ATRIBUTO PRECISA SER 'final'?
 *    - Injeção por Construtor (Boa Prática): O Spring Framework (desde a v4.3) injeta 
 *      dependências automaticamente via construtor único sem a necessidade de usar @Autowired.
 *    - Imutabilidade: O modificador 'final' garante que a referência da dependência 
 *      (ex: 'chamadoRepository') não possa ser alterada/sobrescrita após a inicialização da classe.
 *    - Segurança em Testes: Facilita a criação de testes unitários sem dependência de reflexão 
 *      ou contexto Spring, permitindo passar Mocks diretamente no construtor.
 *
 * 3. @RequiredArgsConstructor VS @AllArgsConstructor:
 *    - @RequiredArgsConstructor (Recomendado para Spring/Injeção): Inclui no construtor apenas
 *      os campos que a classe necessita obrigatoriamente (campos 'final'). Se você adicionar um
 *      atributo mutável de configuração (ex: private String urlApi), ele NÃO entrará no construtor.
 *    - @AllArgsConstructor (Evitar em Controllers/Services): Gera um construtor incluindo TODOS
 *      os atributos declarados. Se houver algum atributo que não seja um Bean do Spring, o container
 *      tentará injetá-lo e lançará o erro NoSuchBeanDefinitionException na inicialização.
 * ============================================================================================
 */

public class ChamadoController {

    private final ChamadoRepository chamadoRepository;
    //private final TecnicoRepository tecnicoRepository;

    // GET /api/chamados — listar todos (com paginação)
    @GetMapping
    public Page<ChamadoResumoDTO> listar(@NonNull Pageable pageable) {
        return chamadoRepository.findAll(pageable)
            .map(ChamadoResumoDTO::new);
    }

    // GET /api/chamados/{id} — buscar um chamado
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoResumoDTO> buscar(@NonNull @PathVariable Long id) {
         return chamadoRepository.findById(id)
                 .map(ChamadoResumoDTO::new)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/chamados — abrir chamado
    // @PostMapping
    // public ResponseEntity<ChamadoDetalhadoDTO> criar(@RequestBody @Valid ChamadoDetalhadoDTO chamado) {
    //     ChamadoDetalhadoDTO salvo = chamadoRepository.save(chamado);
    //     return ResponseEntity.status(201).body(salvo);
    // }

    // // PATCH /api/chamados/{id}/status — atualizar status
    // @PatchMapping("/{id}/status")
    // public ResponseEntity<Chamado> atualizarStatus(
    //         @PathVariable Long id,
    //         @RequestBody Map<String, String> body) {
    //     return chamadoRepository.findById(id).map(chamado -> {
    //         StatusChamado novoStatus = StatusChamado.valueOf(body.get("status"));
    //         chamado.setStatus(novoStatus);
    //         return ResponseEntity.ok(chamadoRepository.save(chamado));
    //     }).orElse(ResponseEntity.notFound().build());
    // }


    // // DELETE /api/chamados/{id}
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deletar(@PathVariable Long id) {
    //     if (!chamadoRepository.existsById(id)) return ResponseEntity.notFound().build();
    //     chamadoRepository.deleteById(id);
    //     return ResponseEntity.noContent().build();
    // }
}
