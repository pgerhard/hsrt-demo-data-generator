package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelAttribut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahlungsmittelAttributRepository extends JpaRepository<ZahlungsmittelAttribut, Long> {
}
