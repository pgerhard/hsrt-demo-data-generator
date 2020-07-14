package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KAEUFER")
public class Kaeufer extends AbstractPersistable<Long> {

  @Column(length = 255)
  private String vorname;

  @Column(length = 255)
  private String nachname;

  @Column(length = 255)
  private String telefonnummer;

  @Column(length = 255)
  private String handynummer;

  @Column(length = 255)
  private String email;

  @Column(length = 255)
  private String username;

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getTelefonnummer() {
    return telefonnummer;
  }

  public void setTelefonnummer(String telefonnummer) {
    this.telefonnummer = telefonnummer;
  }

  public String getHandynummer() {
    return handynummer;
  }

  public void setHandynummer(String handynummer) {
    this.handynummer = handynummer;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
