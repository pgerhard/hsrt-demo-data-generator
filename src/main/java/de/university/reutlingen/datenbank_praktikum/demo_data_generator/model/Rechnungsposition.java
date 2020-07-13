package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "RECHNUNGSPOSITION")
public class Rechnungsposition extends AbstractPersistable<Long> {

  private int menge;

  private BigDecimal preis;

  @ManyToOne
  @JoinColumn(name = "EXEMPLAR_ID")
  private Exemplar exemplar;

  @ManyToOne
  @JoinColumn(name = "RECHNUNGS_ID")
  private Rechnung rechnung;
}
