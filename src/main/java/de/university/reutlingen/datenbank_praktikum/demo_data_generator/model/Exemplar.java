package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table
public class Exemplar extends AbstractPersistable<Long> {

  @Column(name = "SERIENNUMMER")
  private String serienNummer;

  @ManyToOne
  @JoinColumn(name = "PRODUKT_ID")
  private Produkt produkt;
}
