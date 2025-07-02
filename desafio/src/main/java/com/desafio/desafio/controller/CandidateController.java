package com.desafio.desafio.controller;

import com.desafio.desafio.dto.CandidateDTO;
import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.mapper.CandidateMapper;
import com.desafio.desafio.service.CandidateService;
import com.desafio.desafio.service.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Candidatos", description = "Endpoints para gerenciar candidatos")
@RestController
@RequestMapping("/candidatos")
public class CandidateController {

    private final CandidateService candidateService;
    private final ContestService contestService;
    private final CandidateMapper candidateMapper;

    public CandidateController(CandidateService candidateService,
                               ContestService contestService,
                               CandidateMapper candidateMapper) {
        this.candidateService = candidateService;
        this.contestService = contestService;
        this.candidateMapper = candidateMapper;
    }

    @Operation(
            summary = "Listar concursos compatíveis pelo CPF do candidato",
            description = "Retorna concursos públicos compatíveis com as profissões do candidato informado via CPF."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Concursos compatíveis encontrados"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<?> listarConcursosPorCpf(@PathVariable String cpf) {
        Optional<CandidateEntity> candidatoOpt = candidateService.buscarPorCpf(cpf.trim());

        if (candidatoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }

        CandidateEntity candidato = candidatoOpt.get();
        List<String> profissoes = candidato.getProfissoes();

        List<ContestEntity> concursosCompatíveis = contestService.listarTodos().stream()
                .filter(concurso -> concurso.getVagas().stream().anyMatch(profissoes::contains))
                .toList();

        return ResponseEntity.ok(concursosCompatíveis);
    }

    @Operation(
            summary = "Cadastrar novo candidato",
            description = "Cria um novo candidato no banco de dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Candidato criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "CPF já cadastrado")
    })
    @PostMapping
    public ResponseEntity<?> criarCandidato(@RequestBody CandidateDTO candidatoDto) {
        if (candidateService.buscarPorCpf(candidatoDto.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado.");
        }

        CandidateEntity entity = candidateMapper.toEntity(candidatoDto);
        CandidateEntity salvo = candidateService.salvar(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateMapper.toDTO(salvo));
    }

    @Operation(
            summary = "Listar todos os candidatos",
            description = "Retorna uma lista com todos os candidatos cadastrados."
    )
    @GetMapping
    public ResponseEntity<List<CandidateDTO>> listarTodos() {
        List<CandidateDTO> candidatosDto = candidateMapper.toDTOList(candidateService.listarTodos());
        return ResponseEntity.ok(candidatosDto);
    }

    @Operation(
            summary = "Buscar candidato por ID",
            description = "Retorna um candidato específico com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato encontrado"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CandidateDTO> candidatoDTOOpt = candidateService.buscarPorId(id).map(candidateMapper::toDTO);

        if (candidatoDTOOpt.isPresent()) {
            return ResponseEntity.ok(candidatoDTOOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }
    }

    @Operation(
            summary = "Atualizar candidato existente",
            description = "Atualiza os dados de um candidato com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCandidato(@PathVariable Long id, @RequestBody CandidateDTO candidatoDto) {
        Optional<CandidateEntity> existenteOpt = candidateService.buscarPorId(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }

        CandidateEntity existente = existenteOpt.get();
        existente.setNomeCandidate(candidatoDto.getNomeCandidate());
        existente.setDateNasc(candidatoDto.getDateNasc());
        existente.setCpf(candidatoDto.getCpf());
        existente.setProfissoes(candidatoDto.getProfissoes());

        CandidateEntity atualizado = candidateService.salvar(existente);
        return ResponseEntity.ok(candidateMapper.toDTO(atualizado));
    }

    @Operation(
            summary = "Deletar candidato por ID",
            description = "Remove um candidato existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Candidato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Candidato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCandidato(@PathVariable Long id) {
        boolean deletado = candidateService.deletar(id);
        if (deletado) {
            return ResponseEntity.ok("Candidato deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }
    }
}
