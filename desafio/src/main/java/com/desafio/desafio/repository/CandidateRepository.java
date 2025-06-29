package com.desafio.desafio.repository;

import com.desafio.desafio.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {
    Optional<CandidateEntity> findByCpf(String cpf);
}
