package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Zahlungsmittel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahlungsmittelRepository extends JpaRepository<Zahlungsmittel, Long> {

  // nothing to do

}
