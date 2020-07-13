package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemoDataGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataGeneratorApplication.class, args);
	}

}
