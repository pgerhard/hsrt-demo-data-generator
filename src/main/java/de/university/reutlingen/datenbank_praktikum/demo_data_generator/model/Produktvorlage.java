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
@Table(name = "PRODUKTVORLAGE")
public class Produktvorlage extends AbstractPersistable<Long> {

  private String name;

  @ManyToOne
  @JoinColumn(name = "PRODUKTKATEGORIE_ID")
  private Produktkategorie produktkategorie;

}
