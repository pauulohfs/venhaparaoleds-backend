package com.desafio.desafio.service;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.repository.CandidateRepository;
import com.desafio.desafio.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContestService {


    private final ContestRepository contestRepository;

    public ContestService(ContestRepository contestRepository, CandidateRepository candidateRepository) {
        this.contestRepository = contestRepository;
        this.candidateRepository = candidateRepository;
    }

    private final  CandidateRepository candidateRepository;

    // Salvar novo concurso
    public ContestEntity salvar(ContestEntity contest) {
        return contestRepository.save(contest);
    }

    // Listar todos os concursos
    public List<ContestEntity> listarTodos() {
        return contestRepository.findAll();
    }

    // Buscar concurso por ID
    public Optional<ContestEntity> buscarPorId(Long id) {
        return contestRepository.findById(id);
    }

    // Atualizar concurso
    public Optional<ContestEntity> atualizar(Long id, ContestEntity contestAtualizado) {
        return contestRepository.findById(id).map(contestExistente -> {
            contestExistente.setOrgao(contestAtualizado.getOrgao());
            contestExistente.setEdital(contestAtualizado.getEdital());
            contestExistente.setCodigoConcurso(contestAtualizado.getCodigoConcurso());
            contestExistente.setVagas(contestAtualizado.getVagas());
            return contestRepository.save(contestExistente);
        });
    }

    // Deletar concurso
    public boolean deletar(Long id) {
        return contestRepository.findById(id).map(contest -> {
            contestRepository.delete(contest);
            return true;
        }).orElse(false);
    }
    public List<CandidateEntity> listarCandidatosPorCodigoConcurso(String codigoConcurso) {
        Optional<ContestEntity> concursoOpt = contestRepository.findByCodigoConcurso(codigoConcurso);
        if (concursoOpt.isEmpty()) {
            return List.of(); // lista vazia se não achar
        }
        List<String> vagas = concursoOpt.get().getVagas();

        return candidateRepository.findAll().stream()
                .filter(candidato -> candidato.getProfissoes().stream().anyMatch(vagas::contains))
                .toList();
    }

}
