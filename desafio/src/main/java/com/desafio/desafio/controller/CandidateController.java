package com.desafio.desafio.controller;

import com.desafio.desafio.dto.CandidateDTO;
import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.mapper.CandidateMapper;
import com.desafio.desafio.service.CandidateService;
import com.desafio.desafio.service.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "Endpoint para listar concursos compatíveis a partir do CPF do candidato")
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

    @PostMapping
    public ResponseEntity<?> criarCandidato(@RequestBody CandidateDTO candidatoDto) {
        if (candidateService.buscarPorCpf(candidatoDto.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado.");
        }

        CandidateEntity entity = candidateMapper.toEntity(candidatoDto);
        CandidateEntity salvo = candidateService.salvar(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateMapper.toDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> listarTodos() {
        List<CandidateDTO> candidatosDto = candidateMapper.toDTOList(candidateService.listarTodos());
        return ResponseEntity.ok(candidatosDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CandidateDTO> candidatoDTOOpt = candidateService.buscarPorId(id).map(candidateMapper::toDTO);

        if (candidatoDTOOpt.isPresent()) {
            return ResponseEntity.ok(candidatoDTOOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }
    }


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
