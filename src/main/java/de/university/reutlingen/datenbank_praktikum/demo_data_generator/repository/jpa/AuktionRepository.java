package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Auktion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuktionRepository extends JpaRepository<Auktion, Long> {

  // nothing to do

}
