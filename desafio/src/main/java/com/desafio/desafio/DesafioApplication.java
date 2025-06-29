package com.desafio.desafio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@SpringBootApplication
@EntityScan("com.desafio.desafio.entity")
@EnableJpaRepositories(basePackages = "com.desafio.desafio.repository")

public class DesafioApplication {


	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Bean
	public CommandLineRunner verificarConexao(DataSource dataSource) {
		return args -> {
			try {
				dataSource.getConnection().isValid(2);
				System.out.println("✅ Banco conectado com sucesso!");
			} catch (Exception e) {
				System.err.println("❌ Erro ao conectar no banco: " + e.getMessage());
			}
		};
	}
}
