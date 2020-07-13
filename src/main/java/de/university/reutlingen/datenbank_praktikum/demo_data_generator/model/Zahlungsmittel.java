package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.ZahlungsmittelArt;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "ZAHLUNGSMITTEL" )
public class Zahlungsmittel extends AbstractPersistable<Long> {

  @Column(length = 255)
  private String name;

  @ManyToOne
  @JoinColumn(name = "KAEUFER_ID")
  private Kaeufer kaufer;

  @ManyToOne
  @JoinColumn(name = "ZAHLUNGSMITTELART_ID")
  private ZahlungsmittelArt zahlungsmittelart;

}
