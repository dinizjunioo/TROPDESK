package br.com.checklistti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.checklistti.model.Chamado;
import br.com.checklistti.model.StatusChamado;
import br.com.checklistti.model.Tecnico;
import br.com.checklistti.model.RoleTecnico;
import br.com.checklistti.repository.ChamadoRepository;
import br.com.checklistti.repository.TecnicoRepository;
import java.util.Objects;

@SpringBootApplication
public class ChecklistTiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChecklistTiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ChamadoRepository chamadoRepository, TecnicoRepository tecnicoRepository) {
        return args -> {
            System.out.println("Aplicação ChecklistTI iniciada com sucesso!");

            chamadoRepository.deleteAll();
            tecnicoRepository.deleteAll();

            Tecnico t1 = Tecnico.builder()
                            .nome("João da Silva")
                            .email("joao.silva@example.com")
                            .senhaHash("123456789")
                            .role(RoleTecnico.TECNICO)
                            .build();
            Tecnico t2 = Tecnico.builder()
                            .nome("teste da s")
                            .email("teste.silva@example.com")
                            .senhaHash("123412321")
                            .role(RoleTecnico.ADMIN)
                            .build();
            tecnicoRepository.save(Objects.requireNonNull(t1));
            tecnicoRepository.save(Objects.requireNonNull(t2));

            Chamado ch1 = Chamado.builder()
                    .titulo("Chamado de Teste")
                    .descricaoProblema("Descrição do problema de teste")
                    .status(StatusChamado.ABERTO)
                    .tecnico(t1)
                    .build();

            Chamado ch2 = Chamado.builder()
                    .titulo("COMPUTADOR NÃO LIGA")
                    .descricaoProblema("Descrição do problema de teste")
                    .status(StatusChamado.ABERTO)
                    .tecnico(t2)
                    .build();

            chamadoRepository.save(Objects.requireNonNull(ch2));
        

            Chamado ch3 = Chamado.builder()
                    .titulo("IMPRESSORA BORANDO")
                    .descricaoProblema("Descrição do problema de teste")
                    .status(StatusChamado.EM_ANDAMENTO)
                    .tecnico(t2)
                    .build();

            chamadoRepository.save(Objects.requireNonNull(ch1));
            chamadoRepository.save(Objects.requireNonNull(ch2));
            chamadoRepository.save(Objects.requireNonNull(ch3));
        };

    }
}
