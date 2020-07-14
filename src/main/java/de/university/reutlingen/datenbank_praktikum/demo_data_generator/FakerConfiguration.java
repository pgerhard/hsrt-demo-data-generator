package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class FakerConfiguration {

  @Bean
  public Faker faker() {
    return new Faker(Locale.GERMANY);
  }
}
