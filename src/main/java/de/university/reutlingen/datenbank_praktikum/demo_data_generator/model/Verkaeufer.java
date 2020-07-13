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
@Table(name = "VERKAEUFER")
public class Verkaeufer extends AbstractPersistable<Long> {

  @OneToOne
  @JoinColumn(name = "KAEUFER_ID")
  private Kaeufer kaeufer;

  @OneToOne
  @JoinColumn(name = "ADRESSE_ID")
  private Adresse adresse;

  @OneToOne
  @JoinColumn(name = "ZAHLUNGSMITTEL_ID")
  private Zahlungsmittel zahlungsmittel;
}
