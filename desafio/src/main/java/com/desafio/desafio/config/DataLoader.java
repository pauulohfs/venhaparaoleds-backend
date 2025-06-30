package com.desafio.desafio.config;

import com.desafio.desafio.entity.CandidateEntity;
import com.desafio.desafio.entity.ContestEntity;
import com.desafio.desafio.repository.CandidateRepository;
import com.desafio.desafio.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Path path = Paths.get("D:/Spring Java/leds/venhaparaoleds-backend/candidatos.txt");

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo candidatos.txt não encontrado na raiz do projeto.");
        }

        BufferedReader reader = Files.newBufferedReader(path);

        String linha;


        while ((linha = reader.readLine()) != null) {

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

            if (!candidateRepository.existsByCpf(cpf)) {
                CandidateEntity candidato = new CandidateEntity();
                candidato.setNomeCandidate(nome);
                candidato.setDateNasc(Date.from(dataNasc.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                candidato.setCpf(cpf);
                candidato.setProfissoes(profissoes);

                candidateRepository.save(candidato);
            }
        }

        System.out.println("✅ Candidatos importados com sucesso.");
    }


    private void importarConcursos() throws IOException {
        Path path = Paths.get("D:/Spring Java/leds/venhaparaoleds-backend/concursos.txt");

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo concursos.txt não encontrado no caminho absoluto.");
        }

        BufferedReader reader = Files.newBufferedReader(path);
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split("\t");
            if (partes.length < 4) continue;

            String orgao = partes[0].trim();
            String edital = partes[1].trim();
            String codigoConcurso = partes[2].trim();

            List<String> vagas = Arrays.stream(partes[3]
                            .replace("[", "")
                            .replace("]", "")
                            .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            // Evita duplicidade se já existir concurso com esse código
            if (!contestRepository.existsByCodigoConcurso(codigoConcurso)) {
                ContestEntity concurso = new ContestEntity();
                concurso.setOrgao(orgao);
                concurso.setEdital(edital);
                concurso.setCodigoConcurso(codigoConcurso);
                concurso.setVagas(vagas);

                contestRepository.save(concurso);
            }
        }

        System.out.println("✅ Concursos importados com sucesso.");

    }

}
