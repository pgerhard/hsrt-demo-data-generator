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
@Table(name = "ZAHLUNGSMITTELATTRIBUT")
public class ZahlungsmittelAttribut extends AbstractPersistable<Long> {

  private String wert;

  @ManyToOne
  @JoinColumn(name = "ZAHLUNGSMITTEL_ID")
  private Zahlungsmittel zahlungsmittel;

  @ManyToOne
  @JoinColumn(name = "ZAHLUNGSMITTELARTATTRIBUTE_ID")
  private ZahlungsmittelArtAttribut zahlungsmittelArtAttribute;
}
