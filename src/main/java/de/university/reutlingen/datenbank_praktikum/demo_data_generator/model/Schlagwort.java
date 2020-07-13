package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "SCHLAGWORT")
public class Schlagwort extends AbstractPersistable<Long> {

  private String wert;

  private Boolean vordefiniert;

}
