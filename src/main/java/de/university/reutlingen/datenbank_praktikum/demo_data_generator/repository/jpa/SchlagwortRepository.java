package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Schlagwort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchlagwortRepository extends JpaRepository<Schlagwort, Long> {

  // nothing to do

}
