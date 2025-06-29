package com.desafio.desafio.config;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.repository.CandidateRepository;
import com.desafio.desafio.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
    public class DataLoader implements CommandLineRunner {

        @Autowired
        private CandidateRepository candidateRepository;

        @Autowired
        private ContestRepository contestRepository;

        @Override
        public void run(String... args) throws Exception {
            importarCandidatos();
            importarConcursos();
        }

    private void importarCandidatos() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("candidatos.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("Arquivo candidatos.txt não encontrado em resources");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        boolean primeiraLinha = true;

        while ((linha = reader.readLine()) != null) {
            if (primeiraLinha) {
                primeiraLinha = false; // pula o cabeçalho
                continue;
            }

            String[] partes = linha.split("\t");
            if (partes.length < 4) continue;

            String nome = partes[0].trim();
            String dataNascStr = partes[1].trim();
            String cpf = partes[2].trim();

            List<String> profissoes = Arrays.stream(partes[3]
                            .replace("[", "")
                            .replace("]", "")
                            .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNasc = LocalDate.parse(dataNascStr, formatter);

            CandidateEntity candidato = new CandidateEntity();
            candidato.setNomeCandidate(nome);
            candidato.setDateNasc(LocalDate.from(dataNasc.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            candidato.setCpf(cpf);
            candidato.setProfissoes(profissoes);

            candidateRepository.save(candidato);
        }

        System.out.println("✅ Candidatos importados com sucesso.");
    }


        private void importarConcursos() throws IOException {
            // Lê o concursos.txt e persiste no banco
        }
    }


