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
@Table(name = "GEBOT")
public class Gebot extends AbstractPersistable<Long> {

  @Column(name = "ZEITSTEMPEL")
  private Date zeitstempel;

  @Column(name = "HOEHE")
  private BigDecimal hoehe;

  @Column(length = 3)
  private String waehrung;

  @ManyToOne
  @JoinColumn(name = "BIETER_ID")
  private Kaeufer bieter;

  @ManyToOne
  @JoinColumn(name = "AUKTIONS_ID")
  private Auktion auktion;
}
