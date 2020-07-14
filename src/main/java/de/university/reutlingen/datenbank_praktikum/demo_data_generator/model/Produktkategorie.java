package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "PRODUKTKATEGORIE")
public class Produktkategorie extends AbstractPersistable<Long> {

  @Column(length = 255, name = "BEZEICHNUNG")
  private String bezeichnung;

  @ManyToMany
  @JoinTable(
          name = "PRODUKTKATEGORIE_SCHLAGWORT",
          joinColumns = {
                  @JoinColumn(name = "PRODUKTKATEGORIE_ID")
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "SCHLAGWORT_ID")
          }
  )
  private List<Schlagwort> schlagwortList;
}
