package de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdressGenerator {

  @Autowired
  private Faker faker;

  public List<Adresse> generateAdressees(List<Kaeufer> buyers) {

    final List<Adresse> adresses = new ArrayList<>();

    for (Kaeufer buyer : buyers) {
      final int numberOfAdresses = Helper.getRandomNumberInRange(1, 3);
      for (int i = 0; i < numberOfAdresses; i++) {
        final Adresse adresse = new Adresse();

        // 1 in 10 are addressed to someone else
        if (Helper.getRandomNumberInRange(1, 10) > 9) {
          adresse.setVorname(faker.name().firstName());
          adresse.setNachname(faker.name().lastName());

          // 1 in 10 cases should get a Zusatz
          if (Helper.getRandomNumberInRange(1, 10) > 5) {
            final String prefix = Adresse.Zusatz.values()[Helper.getRandomNumberInRange(0, 2)].getRepresentation();
            String vorname = faker.name().firstName();
            String nachname = faker.name().lastName();
            adresse.setAdresszusatz(String.format("%s %s %s", prefix, vorname, nachname));
          }
        }

        adresse.setStrasse(faker.address().streetName());
        adresse.setHausnummer(Integer.valueOf(faker.address().streetAddressNumber()));
        adresse.setPlz(faker.address().zipCode());
        adresse.setStadt(faker.address().city());
        adresse.setBundesland(faker.address().state());
        adresse.setLand("Deutschland");
        adresse.setKaeufer(buyer);
        if (Helper.getRandomNumberInRange(1, 10) > 9) {
          adresse.setArt(Adresse.Art.RECHNUNGSADRESSE);
        } else {
          adresse.setArt(Adresse.Art.LIEFERADRESSE);
        }

        adresses.add(adresse);
      }
    }

    return adresses;
  }

}
