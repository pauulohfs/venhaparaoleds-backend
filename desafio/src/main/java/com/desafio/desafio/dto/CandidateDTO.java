package com.desafio.desafio.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor


public class CandidateDTO {

    private Long idCandidate;
    private String nomeCandidate;
    private Date dateNasc;
    private String cpf;
    private List<String> profissoes;


}
