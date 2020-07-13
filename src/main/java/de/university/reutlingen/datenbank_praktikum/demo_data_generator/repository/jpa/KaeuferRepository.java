package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;


import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaeuferRepository extends JpaRepository<Kaeufer, Long> {

  // nothing to do

}
