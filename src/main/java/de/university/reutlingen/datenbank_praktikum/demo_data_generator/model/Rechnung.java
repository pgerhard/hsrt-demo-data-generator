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
@Table(name = "RECHNUNG")
public class Rechnung extends AbstractPersistable<Long> {

  @Column(name = "RECHNUNGSDATUM")
  private Date rechnungsDatum;

  @Column(name = "LIEFERDATUM")
  private Date lieferDatum;

  @Column(name = "ZAHLUNGSDATUM")
  private Date zahlungsDatum;

  @Column(name = "UEBERWEISUNGSDATUM")
  private Date ueberweisungsDatum;

  @Column(name = "RECHNUNGSNUMMER")
  private BigDecimal rechnungsNummer;

  @ManyToOne
  @JoinColumn(name = "LIEFERADRESSE_ID")
  private Adresse lieferAdresse;

  @ManyToOne
  @JoinColumn(name = "RECHNUNGSADRESSE_ID")
  private Adresse rechnungsAdresse;

  @ManyToOne
  @JoinColumn(name = "VERKAUFSADRESSE_ID")
  private Adresse verkaeuferAdresse;
}
