package com.desafio.desafio.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor


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


}

