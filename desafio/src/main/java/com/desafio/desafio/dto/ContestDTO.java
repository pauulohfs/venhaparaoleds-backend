package com.desafio.desafio.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor


public class ContestDTO {

    private Long id;
    private String orgao;
    private String edital;
    private String codigoConcurso;
    private List<String> vagas;


}
