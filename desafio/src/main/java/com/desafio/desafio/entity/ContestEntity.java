package com.desafio.desafio.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "contest")
public class ContestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orgao;

    @Column(nullable = false)
    private String edital;

    @Column(nullable = false, unique = true)
    private String codigoConcurso;

    @ElementCollection
    @CollectionTable(
            name = "jobs_contest",
            joinColumns = @JoinColumn(name = "contest_id")
    )
    @Column(name = "jobs")
    private List<String> vagas;

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getEdital() {
        return edital;
    }

    public void setEdital(String edital) {
        this.edital = edital;
    }

    public String getCodigoConcurso() {
        return codigoConcurso;
    }

    public void setCodigoConcurso(String codigoConcurso) {
        this.codigoConcurso = codigoConcurso;
    }

    public List<String> getVagas() {
        return vagas;
    }

    public void setVagas(List<String> vagas) {
        this.vagas = vagas;
    }
}

