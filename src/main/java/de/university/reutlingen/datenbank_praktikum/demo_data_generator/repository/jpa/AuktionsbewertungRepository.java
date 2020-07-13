package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Auktionsbewertung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuktionsbewertungRepository extends JpaRepository<Auktionsbewertung, Long> {

  // nothing to do

}
