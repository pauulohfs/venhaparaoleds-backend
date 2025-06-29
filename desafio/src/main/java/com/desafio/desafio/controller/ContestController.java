package com.desafio.desafio.controller;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/concursos")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @GetMapping("/candidatos/{codigoConcurso}")
    public ResponseEntity<List<CandidateEntity>> listarCandidatosPorConcurso(@PathVariable String codigoConcurso) {
        List<CandidateEntity> candidatos = contestService.listarCandidatosPorCodigoConcurso(codigoConcurso);

        if (candidatos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(candidatos);
    }

    @PostMapping
    public ResponseEntity<ContestEntity> criar(@RequestBody ContestEntity contest) {
        ContestEntity criado = contestService.salvar(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public List<ContestEntity> listarTodos() {
        return contestService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContestEntity> buscarPorId(@PathVariable Long id) {
        return contestService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContestEntity> atualizar(@PathVariable Long id, @RequestBody ContestEntity contest) {
        return contestService.atualizar(id, contest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (contestService.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

