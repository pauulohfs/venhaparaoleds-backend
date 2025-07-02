package com.desafio.desafio.integration;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandidateIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void testCriarEBuscarCandidato() {
        // Limpa antes para não interferir
        candidateRepository.deleteAll();

        CandidateEntity candidato = new CandidateEntity();
        candidato.setNomeCandidate("Maria");
        candidato.setCpf("12345678900");
        candidato.setDateNasc(new Date());


        // Cria o candidato via API POST
        ResponseEntity<CandidateEntity> postResponse = restTemplate.postForEntity(
                "/candidatos",
                candidato,
                CandidateEntity.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        CandidateEntity candidatoCriado = postResponse.getBody();
        assertThat(candidatoCriado).isNotNull();
        assertThat(candidatoCriado.getNomeCandidate()).isEqualTo("Maria");

        // Busca o candidato via API GET
        ResponseEntity<CandidateEntity> getResponse = restTemplate.getForEntity(
                "/candidatos/id/" + candidatoCriado.getIdCandidate(),
                CandidateEntity.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        CandidateEntity candidatoBuscado = getResponse.getBody();
        assertThat(candidatoBuscado).isNotNull();
        assertThat(candidatoBuscado.getNomeCandidate()).isEqualTo("Maria");
    }
}
