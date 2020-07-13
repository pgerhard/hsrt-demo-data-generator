package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "VERKAEUFERBEWERTUNG")
public class Verkaeuferbewertung extends AbstractPersistable<Long> {

  private BigDecimal wert;

  private String kommentar;

  @Column(name = "ERSTELLUNGSDATUM")
  private Date erstellungsDatum;

  @ManyToOne
  @JoinColumn(name = "VERKAEUFER_ID")
  private Verkaeufer verkaeufer;

  @ManyToOne
  @JoinColumn(name = "BEWERTER_ID")
  private Kaeufer bewerter;

}
