package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktmerkmal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduktmerkmalRepository extends JpaRepository<Produktmerkmal, Long> {

  // nothing to do

}
