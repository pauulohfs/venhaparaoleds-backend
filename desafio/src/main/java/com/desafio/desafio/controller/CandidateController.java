package com.desafio.desafio.controller;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.service.CandidateService;
import com.desafio.desafio.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidatos")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ContestService contestService;

    // Endpoint para listar concursos compatíveis a partir do CPF do candidato
    @GetMapping("/{cpf}")
    public ResponseEntity<?> listarConcursosPorCpf(@PathVariable String cpf) {
        Optional<CandidateEntity> candidatoOpt = candidateService.buscarPorCpf(cpf.trim());

        if (candidatoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }

        CandidateEntity candidato = candidatoOpt.get();
        List<String> profissoes = candidato.getProfissoes();

        // Lista todos concursos e filtra os que têm vagas compatíveis
        List<ContestEntity> concursosCompatíveis = contestService.listarTodos().stream()
                .filter(concurso -> concurso.getVagas().stream().anyMatch(profissoes::contains))
                .collect(Collectors.toList());

        return ResponseEntity.ok(concursosCompatíveis);
    }

    // CREATE - POST /candidatos
    @PostMapping
    public ResponseEntity<?> criarCandidato(@RequestBody CandidateEntity candidato) {
        if (candidateService.buscarPorCpf(candidato.getCpf()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado.");
        }
        CandidateEntity salvo = candidateService.salvar(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // READ ALL - GET /candidatos
    @GetMapping
    public ResponseEntity<List<CandidateEntity>> listarTodos() {
        List<CandidateEntity> candidatos = candidateService.listarTodos();
        return ResponseEntity.ok(candidatos);
    }

    // READ BY ID - GET /candidatos/id/{id}
    @GetMapping("/id/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CandidateEntity> candidatoOpt = candidateService.buscarPorId(id);
        if (candidatoOpt.isPresent()) {
            return ResponseEntity.ok(candidatoOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }
    }

    // UPDATE - PUT /candidatos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCandidato(@PathVariable Long id, @RequestBody CandidateEntity candidatoAtualizado) {
        Optional<CandidateEntity> atualizado = candidateService.atualizar(id, candidatoAtualizado);
        if (atualizado.isPresent()) {
            return ResponseEntity.ok(atualizado.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado.");
        }
    }

    // DELETE - DELETE /candidatos/{id}
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
