package com.desafio.desafio.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    public String getNomeCandidate() {
        return nomeCandidate;
    }

    public void setNomeCandidate(String nomeCandidate) {
        this.nomeCandidate = nomeCandidate;
    }

    public Date getDateNasc() {
        return dateNasc;
    }

    public void setDateNasc(Date dateNasc) {
        this.dateNasc = dateNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<String> getProfissoes() {
        return profissoes;
    }

    public void setProfissoes(List<String> profissoes) {
        this.profissoes = profissoes;
    }
}
