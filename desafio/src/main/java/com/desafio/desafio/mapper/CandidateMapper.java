package com.desafio.desafio.mapper;

import com.desafio.desafio.dto.CandidateDTO;
import com.desafio.desafio.entity.CandidateEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateMapper {

    public CandidateDTO toDTO(CandidateEntity entity) {
        if (entity == null) return null;

        CandidateDTO dto = new CandidateDTO();
        dto.setIdCandidate(entity.getIdCandidate());
        dto.setNomeCandidate(entity.getNomeCandidate());
        dto.setDateNasc(entity.getDateNasc());
        dto.setCpf(entity.getCpf());
        dto.setProfissoes(entity.getProfissoes());
        return dto;
    }

    public CandidateEntity toEntity(CandidateDTO dto) {
        if (dto == null) return null;

        CandidateEntity entity = new CandidateEntity();
        entity.setIdCandidate(dto.getIdCandidate());
        entity.setNomeCandidate(dto.getNomeCandidate());
        entity.setDateNasc(dto.getDateNasc());
        entity.setCpf(dto.getCpf());
        entity.setProfissoes(dto.getProfissoes());
        return entity;
    }

    public List<CandidateDTO> toDTOList(List<CandidateEntity> entityList) {
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<CandidateEntity> toEntityList(List<CandidateDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
