package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeuferbewertung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaeuferbewertungRepository extends JpaRepository<Kaeuferbewertung, Long> {

  // nothing to do

}
