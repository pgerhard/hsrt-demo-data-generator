package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ZAHLUNGSMITTELARTATTRIBUT")
public class ZahlungsmittelArtAttribut extends AbstractPersistable<Long> {

  private String name;

  private String muster;

  @ManyToMany
  @JoinTable(
          name = "ZMTLART_ZMTLARTATTR",
          joinColumns = {
                  @JoinColumn(name = "zhlngsmittlartattribute_id", referencedColumnName = "ID"),
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "zhlngsmittlart_id", referencedColumnName = "ID")
          }
  )
  private List<ZahlungsmittelArt> arten;
}
