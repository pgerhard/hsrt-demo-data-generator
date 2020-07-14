package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "PRODUKT")
public class Produkt extends AbstractPersistable<Long> {

  private String bezeichnung;

  private String beschreibung;

  @OneToOne
  @JoinColumn(name = "PRODUKTKATEGORIE_ID")
  private Produktkategorie produktkategorie;

  @ManyToMany
  @JoinTable(
          name = "PRODUKT_SCHLAGWORT",
          joinColumns = {
                  @JoinColumn(name = "PRODUKT_ID")
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "SCHLAGWORT_ID")
          }
  )
  private List<Schlagwort> schlagwortList;
}
