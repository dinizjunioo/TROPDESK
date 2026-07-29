# Checklist T.I. — Backend

> App para técnicos de T.I. registrarem chamados, problemas, checklists de serviço e localização de ferramentas.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.2 |
| Banco (dev) | H2 (em memória, sem instalar nada) |
| Banco (prod) | PostgreSQL 15 |
| Containers | Docker + docker-compose |
| Build | Maven |
| Segurança | Spring Security + JWT |

---

## Pré-requisitos

Antes de rodar o projeto, instale:

### 1. Java 21
Baixe o instalador em:
```
https://adoptium.net/temurin/releases/?version=21
```
Confirme a instalação:
```bash
java -version
```

### 2. Maven
**Windows (com Chocolatey):**
```powershell
choco install maven
```
**Windows (manual):**
Baixe o ZIP em `https://maven.apache.org/download.cgi`, descompacte em `C:\maven` e adicione `C:\maven\bin` no PATH.

**Mac:**
```bash
brew install maven
```
Confirme a instalação:
```bash
mvn -version
```

### 3. Docker (para rodar com banco PostgreSQL)
- **Mac Big Sur (Intel):** instale o Colima (substituto do Docker Desktop)
```bash
brew install colima docker docker-compose
colima start --cpu 2 --memory 4
```
- **Windows / Mac Monterey ou superior:** baixe o Docker Desktop em `https://www.docker.com/products/docker-desktop`

---

## Extensões recomendadas no VS Code

Cole no terminal para instalar todas de uma vez:

```bash
code --install-extension vscjava.vscode-java-pack
code --install-extension vmware.vscode-spring-boot
code --install-extension vscjava.vscode-spring-initializr
code --install-extension vscjava.vscode-spring-boot-dashboard
code --install-extension cweijan.vscode-database-client2
code --install-extension eamodio.gitlens
code --install-extension GabrielBB.vscode-lombok
code --install-extension redhat.vscode-xml
```

| Extensão | Para quê serve |
|---|---|
| vscode-java-pack | suporte completo a Java |
| vscode-spring-boot | autocomplete Spring Boot |
| vscode-spring-initializr | criar projetos Spring pelo VS Code |
| vscode-spring-boot-dashboard | botão play/stop da API na barra lateral |
| vscode-database-client2 | ver H2 e PostgreSQL dentro do VS Code |
| gitlens | histórico Git no código |
| vscode-lombok | suporte ao @Data, @RequiredArgsConstructor etc |
| vscode-xml | formatar o pom.xml |

---

## Como rodar

### Fase 1 — desenvolvimento local (sem Docker, banco H2)

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/checklist-ti.git
cd checklist-ti

# Rode a aplicação
mvn spring-boot:run
```

API disponível em: `http://localhost:8080`

Console do banco H2 (para inspecionar os dados): `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:checklistti`
- User: `sa`
- Password: *(vazio)*

### Fase 2 — com Docker e PostgreSQL

```bash
# Sobe a API + banco juntos
docker compose up

# Para parar
docker compose down
```

API disponível em: `http://localhost:8080`

---

## Estrutura do projeto

```
checklist-ti/
├── Dockerfile                        ← imagem Docker da API
├── docker-compose.yml                ← API + PostgreSQL juntos
├── pom.xml                           ← dependências Maven
└── src/main/
    ├── java/br/com/checklistti/
    │   ├── ChecklistTiApplication.java   ← ponto de entrada
    │   ├── model/
    │   │   ├── Chamado.java              ← entidade principal
    │   │   ├── ItemChecklist.java        ← itens do checklist
    │   │   ├── Tecnico.java              ← técnico responsável
    │   │   ├── StatusChamado.java        ← enum de status
    │   │   └── RoleTecnico.java          ← enum de perfil
    │   ├── repository/                   ← acesso ao banco
    │   │   ├── ChamadoRepository.java
    │   │   ├── ItemChecklistRepository.java
    │   │   └── TecnicoRepository.java
    │   └── controller/
    │       ├── ChamadoController.java    ← endpoints de chamados
    │       └── ChecklistController.java  ← endpoints do checklist
    └── resources/
        ├── application.properties        ← configuração geral
        ├── application-dev.properties    ← H2 local
        └── application-prod.properties   ← PostgreSQL
```

---

## Endpoints da API

### Chamados

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /api/chamados | Listar chamados (paginado) |
| POST | /api/chamados | Abrir novo chamado |
| GET | /api/chamados/{id} | Buscar chamado por ID |
| PATCH | /api/chamados/{id}/status | Atualizar status |
| PATCH | /api/chamados/{id}/ferramentas | Registrar local da ferramenta |
| DELETE | /api/chamados/{id} | Deletar chamado |

### Checklist

| Método | Endpoint | Descrição |
|---|---|---|
| GET | /api/chamados/{id}/checklist | Listar itens do checklist |
| POST | /api/chamados/{id}/checklist | Adicionar item |
| PATCH | /api/checklist/{id} | Marcar item como feito |
| DELETE | /api/checklist/{id} | Deletar item |

---

## Fluxo de status do chamado

```
ABERTO → EM_ANDAMENTO → FERRAMENTA_NO_LOCAL → CONCLUIDO
```

O status `FERRAMENTA_NO_LOCAL` é ativado automaticamente ao registrar o local da ferramenta via `PATCH /api/chamados/{id}/ferramentas`. Isso evita encerrar um chamado sem antes registrar onde o equipamento foi deixado.

---

## Roadmap

| Fase | O que entra |
|---|---|
| Fase 1 | Texto — chamados, checklist, local de ferramentas |
| Fase 2 | Equipe — autenticação JWT, múltiplos técnicos, app Android |
| Fase 3 | Nuvem — deploy AWS (Elastic Beanstalk + RDS), fotos no S3 |

---

## Variáveis de ambiente (produção)

| Variável | Descrição | Padrão |
|---|---|---|
| DB_URL | URL do banco PostgreSQL | jdbc:postgresql://localhost:5432/checklistti |
| DB_USER | Usuário do banco | tecnico |
| DB_PASS | Senha do banco | senha123 |
| SPRING_PROFILES_ACTIVE | Perfil ativo | dev |

---

## Contribuindo

1. Crie uma branch a partir da `main`: `git checkout -b feature/nome-da-feature`
2. Faça suas alterações e commit: `git commit -m "feat: descrição"`
3. Abra um Pull Request para revisão antes de mergear

---

## Licença

Uso interno — Equipe de T.I.
