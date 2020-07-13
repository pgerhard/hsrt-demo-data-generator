package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Verkaeufer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerkaeuferRepository extends JpaRepository<Verkaeufer, Long> {

  // nothing to do

}
