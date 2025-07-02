package com.desafio.desafio.controller;

import com.desafio.desafio.dto.CandidateDTO;
import com.desafio.desafio.dto.ContestDTO;
import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.mapper.CandidateMapper;
import com.desafio.desafio.service.ContestService;
import com.desafio.desafio.mapper.ContestMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/concursos")
@Tag(name = "Concursos", description = "Endpoints para gerenciar concursos")
public class ContestController {

    private final ContestService contestService;
    private final ContestMapper contestMapper;
    private final CandidateMapper candidateMapper;

    public ContestController(
            ContestService contestService,
            ContestMapper contestMapper,
            CandidateMapper candidateMapper
    ) {
        this.contestService = contestService;
        this.contestMapper = contestMapper;
        this.candidateMapper = candidateMapper;
    }

    @GetMapping("/candidatos/{codigoConcurso}")
    public ResponseEntity<List<CandidateDTO>> listarCandidatosPorConcurso(@PathVariable String codigoConcurso) {
        List<CandidateEntity> candidatos = contestService.listarCandidatosPorCodigoConcurso(codigoConcurso);
        if (candidatos.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(candidateMapper.toDTOList(candidatos));
    }

    @PostMapping
    public ResponseEntity<ContestDTO> criar(@RequestBody ContestDTO contestDto) {
        var criado = contestMapper.toDTO(contestService.salvar(contestMapper.toEntity(contestDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<ContestDTO>> listarTodos() {
        return ResponseEntity.ok(
                contestService.listarTodos().stream().map(contestMapper::toDTO).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestDTO> buscarPorId(@PathVariable Long id) {
        return contestService.buscarPorId(id)
                .map(contestMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContestDTO> atualizar(@PathVariable Long id, @RequestBody ContestDTO contestDto) {
        return contestService.atualizar(id, contestMapper.toEntity(contestDto))
                .map(contestMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return contestService.deletar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

