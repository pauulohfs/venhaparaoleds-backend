package com.desafio.desafio.service;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    public void deveSalvarCandidatoQuandoCpfNaoExiste() {
        CandidateEntity candidato = new CandidateEntity();
        candidato.setNomeCandidate("João");
        candidato.setCpf("12345678900");

        when(candidateRepository.existsByCpf("12345678900")).thenReturn(false);
        when(candidateRepository.save(any())).thenReturn(candidato);

        CandidateEntity salvo = candidateService.salvarSeNaoExistir(candidato);

        assertNotNull(salvo);
        verify(candidateRepository, times(1)).save(candidato);
    }

    @Test
    public void naoDeveSalvarCandidatoSeCpfExistir() {
        CandidateEntity candidato = new CandidateEntity();
        candidato.setCpf("12345678900");

        when(candidateRepository.existsByCpf("12345678900")).thenReturn(true);

        CandidateEntity salvo = candidateService.salvarSeNaoExistir(candidato);

        assertNull(salvo);
        verify(candidateRepository, never()).save(any());
    }
}
