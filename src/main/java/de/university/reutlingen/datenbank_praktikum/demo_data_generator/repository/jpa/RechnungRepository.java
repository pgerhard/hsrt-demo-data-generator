package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Rechnung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RechnungRepository extends JpaRepository<Rechnung, Long> {

  // nothing to do

}
