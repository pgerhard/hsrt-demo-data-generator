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
@Table(name = "PRODUKTVORLAGEMERKMAL")
public class Produktvorlagemerkmal extends AbstractPersistable<Long> {

  private String name;

  private Boolean bearbeitbar;

  private String startwert;

  @ManyToMany
  @JoinTable(
          name = "PRDKTVRLG_PRDKTVRLGMRKML",
          joinColumns = {
                  @JoinColumn(name = "PRODUKTVORLAGEMERKMAL_ID")
          },
          inverseJoinColumns = {
                  @JoinColumn(name = "PRODUKTVORLAGE_ID")
          }
  )
  private List<Produktvorlage> produktvorlagen;
}
