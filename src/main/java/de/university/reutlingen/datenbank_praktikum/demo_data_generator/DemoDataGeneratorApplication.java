package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@EnableJpaRepositories
public class DemoDataGeneratorApplication {

  public static Date REFERENCE_DATE;

  public static void main(String[] args) {
    final Calendar calendar = Calendar.getInstance();
    calendar.set(2020, Calendar.JULY, Calendar.WEDNESDAY, 0, 0, 0);
    REFERENCE_DATE = calendar.getTime();

    SpringApplication.run(DemoDataGeneratorApplication.class, args);
  }

}
