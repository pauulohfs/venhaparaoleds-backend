package com.desafio.desafio.controller;

import com.desafio.desafio.dto.CandidateDTO;
import com.desafio.desafio.dto.ContestDTO;
import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.service.ContestService;
import com.desafio.desafio.mapper.ContestMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Concursos", description = "Endpoints para gerenciar concursos")
@RestController
@RequestMapping("/concursos")
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/candidatos/{codigoConcurso}")
    public ResponseEntity<List<CandidateDTO>> listarCandidatosPorConcurso(@PathVariable String codigoConcurso) {
        List<CandidateEntity> candidatos = contestService.listarCandidatosPorCodigoConcurso(codigoConcurso);

        if (candidatos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CandidateDTO> candidatosDto = candidatos.stream()
                .map(candidate -> {
                    CandidateDTO dto = new CandidateDTO();
                    dto.setIdCandidate(candidate.getIdCandidate());
                    dto.setNomeCandidate(candidate.getNomeCandidate());
                    dto.setCpf(candidate.getCpf());
                    dto.setDateNasc(candidate.getDateNasc());
                    dto.setProfissoes(candidate.getProfissoes());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(candidatosDto);
    }

    @PostMapping
    public ResponseEntity<ContestDTO> criar(@RequestBody ContestDTO contestDto) {
        ContestDTO criado = ContestMapper.toDTO(contestService.salvar(ContestMapper.toEntity(contestDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<ContestDTO>> listarTodos() {
        List<ContestDTO> lista = contestService.listarTodos()
                .stream()
                .map(ContestMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestDTO> buscarPorId(@PathVariable Long id) {
        return contestService.buscarPorId(id)
                .map(ContestMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContestDTO> atualizar(@PathVariable Long id, @RequestBody ContestDTO contestDto) {
        ContestDTO atualizado = contestService.atualizar(id, ContestMapper.toEntity(contestDto))
                .map(ContestMapper::toDTO)
                .orElse(null);

        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = contestService.deletar(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
