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
@Table(name = "AUKTION")
public class Auktion extends AbstractPersistable<Long> {

  @Column(name = "STARTZEITPUNKT")
  private Date startZeitPunkt;
  @Column(name = "ENDZEITPUNKT")
  private Date endZeitPunkt;
  private Boolean aktiviert;
  private BigDecimal startpreis;
  @Column(length = 3)
  private String waehrung;
  private BigDecimal mindestpreis;
  @Column(name = "SOFORTKAUFPREIS")
  private BigDecimal sofortKaufPreis;
  private String art;
  @Column(name = "VERSANDART")
  private String versandArt;
  @Column(name = "VERSANDKOSTEN")
  private BigDecimal versandKosten;
  @ManyToOne
  @JoinColumn(name = "ANBIETER_ID")
  private Verkaeufer anbieter;
  @ManyToOne
  @JoinColumn(name = "EXEMPLAR_ID")
  private Exemplar exemplar;
  @ManyToOne
  @JoinColumn(name = "KAEUFER_ID")
  private Kaeufer kaeufer;
  @ManyToOne
  @JoinColumn(name = "PRODUKT_ID")
  private Produkt produkt;

  public enum Art {
    AUKTION("Auktion"),
    SOFORT_KAUF("Sofort-Art"),
    GEMISCHT("Gemischt");

    private String representation;

    Art(String representation) {
      this.representation = representation;
    }

    public String getRepresentation() {
      return representation;
    }
  }

  public enum VersandArt {
    ABHOLUNG("Abholung"),
    POST("Post"),
    SPEDITION("Spedition");

    private String representation;

    VersandArt(String representation) {
      this.representation = representation;
    }

    public String getRepresentation() {
      return representation;
    }
  }
}
