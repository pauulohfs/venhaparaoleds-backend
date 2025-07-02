package com.desafio.desafio.mapper;

import com.desafio.desafio.dto.ContestDTO;
import com.desafio.desafio.entity.ContestEntity;
import org.springframework.stereotype.Component;

@Component
public class ContestMapper {

    public  ContestDTO toDTO(ContestEntity entity) {
        if (entity == null) {
            return null;
        }
        ContestDTO dto = new ContestDTO();
        dto.setId(entity.getId());
        dto.setOrgao(entity.getOrgao());
        dto.setEdital(entity.getEdital());
        dto.setCodigoConcurso(entity.getCodigoConcurso());
        dto.setVagas(entity.getVagas());
        return dto;
    }

    public  ContestEntity toEntity(ContestDTO dto) {
        if (dto == null) {
            return null;
        }
        ContestEntity entity = new ContestEntity();
        entity.setId(dto.getId());  // importante para update
        entity.setOrgao(dto.getOrgao());
        entity.setEdital(dto.getEdital());
        entity.setCodigoConcurso(dto.getCodigoConcurso());
        entity.setVagas(dto.getVagas());
        return entity;
    }
}
