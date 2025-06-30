package com.desafio.desafio.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;



import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(hidden = true)
@Entity
@Table(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCandidate;

    @Column(nullable = false)
    private String nomeCandidate;


    @Temporal(TemporalType.DATE)
    @Column(nullable = false, updatable = false)
    private Date dateNasc;

    @Column(nullable = false, unique = true)
    private String cpf;

    @ElementCollection
    @CollectionTable(
            name = "profisse_candidate",
            joinColumns = @JoinColumn(name = "candidate_id")
    )
    @Column(name = "profisse")
    private List<String> profissoes;


}
