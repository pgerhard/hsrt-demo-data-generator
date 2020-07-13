package de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktkategorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduktkategorieRepository extends JpaRepository<Produktkategorie, Long> {

  // nothing to do

}
