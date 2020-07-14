package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Rechnungsposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RechnungspositionRepository extends JpaRepository<Rechnungsposition, Long> {

  // nothing to do

}
