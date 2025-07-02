package com.desafio.desafio.service;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {


    private final CandidateRepository candidateRepository;


    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    // Salvar novo candidato
    public CandidateEntity salvar(CandidateEntity candidato) {
        return candidateRepository.save(candidato);
    }

    // Listar todos os candidatos
    public List<CandidateEntity> listarTodos() {
        return candidateRepository.findAll();
    }

    // Buscar candidato por ID
    public Optional<CandidateEntity> buscarPorId(Long id) {
        return candidateRepository.findById(id);
    }

    // Buscar candidato por CPF
    public Optional<CandidateEntity> buscarPorCpf(String cpf) {
        return candidateRepository.findByCpf(cpf);
    }

    // Atualizar candidato
    public Optional<CandidateEntity> atualizar(Long id, CandidateEntity candidatoAtualizado) {
        return candidateRepository.findById(id).map(candidatoExistente -> {
            candidatoExistente.setNomeCandidate(candidatoAtualizado.getNomeCandidate());
            candidatoExistente.setDateNasc(candidatoAtualizado.getDateNasc());
            candidatoExistente.setCpf(candidatoAtualizado.getCpf());
            candidatoExistente.setProfissoes(candidatoAtualizado.getProfissoes());
            return candidateRepository.save(candidatoExistente);
        });
    }

    // Deletar candidato
    public boolean deletar(Long id) {
        return candidateRepository.findById(id).map(candidato -> {
            candidateRepository.delete(candidato);
            return true;
        }).orElse(false);
    }

    public CandidateEntity salvarSeNaoExistir(CandidateEntity candidato) {
        if (candidateRepository.existsByCpf(candidato.getCpf())) {
            return null;
        }
        return candidateRepository.save(candidato);
    }

}
