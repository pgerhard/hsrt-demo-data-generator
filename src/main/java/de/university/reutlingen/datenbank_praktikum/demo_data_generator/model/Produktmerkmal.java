package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "PRODUKTMERKMAL")
public class Produktmerkmal extends AbstractPersistable<Long> {

  private String wert;

  @ManyToOne
  @JoinColumn(name = "PRODUKT_ID")
  private Produkt produkt;

  @ManyToOne
  @JoinColumn(name = "PRODUKTVORLAGEMERKMAL_ID")
  private Produktvorlagemerkmal produktvorlagemerkmal;
}
