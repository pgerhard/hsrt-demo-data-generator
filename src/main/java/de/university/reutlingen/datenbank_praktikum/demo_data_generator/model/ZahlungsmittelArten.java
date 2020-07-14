package de.university.reutlingen.datenbank_praktikum.demo_data_generator.model;

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
