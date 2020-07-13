package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "PRODUKT")
public class Produkt extends AbstractPersistable<Long> {

  private String bezeichnung;

  private String beschreibung;

  @OneToOne
  @JoinColumn(name = "PRODUKTKATEGORIE_ID")
  private Produktkategorie produktkategorie;
}
