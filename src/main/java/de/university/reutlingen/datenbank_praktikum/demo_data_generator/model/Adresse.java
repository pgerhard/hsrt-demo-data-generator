package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ADRESSE")
public class Adresse extends AbstractPersistable<Long> {

  private String vorname;
  private String nachname;
  private String strasse;
  private Integer hausnummer;
  private String adresszusatz;
  private String plz;
  private String stadt;
  private String bundesland;
  private String land;
  @Enumerated(EnumType.STRING)
  private Art art;
  @ManyToOne
  private Kaeufer kaeufer;

  public enum Art {
    LIEFERADRESSE,
    RECHNUNGSADRESSE;
  }

  public enum Zusatz {
    CO("c/o"),
    ZH("z. Hd."),
    OVA("o.V.i.A.");

    private String representation;

    Zusatz(String representation) {
      this.representation = representation;
    }

    public String getRepresentation() {
      return representation;
    }
  }

}
