package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ZAHLUNGSMITTELART")
public class ZahlungsmittelArt extends AbstractPersistable<Long> {

  @Column(length = 255)
  private String name;

  public enum ZahlungsmittelArten {

    RECHNUNG("Rechnung"),
    TRANSFER("Überweisung"),
    PAYPAL("Paypal"),
    CREDIT_CARD("Kreditkarte"),
    SEPA("SEPA-Lastschrift");

    private String representation;

    ZahlungsmittelArten(String representation) {
      this.representation = representation;
    }

    public String getRepresentation() {
      return representation;
    }
  }

}
