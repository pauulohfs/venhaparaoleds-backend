package com.desafio.desafio.repository;

import com.desafio.desafio.entity.ContestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<ContestEntity, Long> {
    Optional<ContestEntity> findByCodigoConcurso(String codigoConcurso);

    // Para buscar todos concursos por vaga (ex: "carpinteiro")
    List<ContestEntity> findByVagasContainingIgnoreCase(String vaga);
}
