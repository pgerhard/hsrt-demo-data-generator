package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.DemoAddressService.getRandomNumberInRange;
import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.DemoDataGeneratorApplication.REFERENCE_DATE;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Auktion;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Auktionsbewertung;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeuferbewertung;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Verkaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Verkaeuferbewertung;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingGenerator {

  @Autowired
  private Faker faker;

  public RatingData generateRatingData(List<Verkaeufer> verkaeuferList,
                                       List<Kaeufer> kaeuferList,
                                       List<Auktion> auktionList) {

    final ArrayList<Verkaeuferbewertung> verkaeuferbewertungList = new ArrayList<>();
    for (Verkaeufer seller : verkaeuferList) {
      if (getRandomNumberInRange(1, 10) > 3) {
        for (int i = 0; i < getRandomNumberInRange(1, 100); i++) {
          final Verkaeuferbewertung verkaeuferbewertung = new Verkaeuferbewertung();
          Kaeufer bewerter;
          do {
            bewerter = kaeuferList.get(getRandomNumberInRange(0, kaeuferList.size() - 1));
          } while (bewerter.equals(seller.getKaeufer()));

          verkaeuferbewertung.setBewerter(bewerter);
          verkaeuferbewertung.setErstellungsDatum(DateUtils.addDays(REFERENCE_DATE, -1 * getRandomNumberInRange(1, 10)));
          verkaeuferbewertung.setKommentar(StringUtils.join(faker.lorem().words(5), " "));
          verkaeuferbewertung.setWert(new BigDecimal(getRandomNumberInRange(0, 5)));
          verkaeuferbewertung.setVerkaeufer(seller);

          verkaeuferbewertungList.add(verkaeuferbewertung);
        }
      }
    }

    final ArrayList<Kaeuferbewertung> kaeuferbewertungList = new ArrayList<>();
    for (Kaeufer buyer : kaeuferList) {
      if (getRandomNumberInRange(1, 10) > 3) {
        for (int i = 0; i < getRandomNumberInRange(1, 100); i++) {
          final Kaeuferbewertung kaeuferbewertung = new Kaeuferbewertung();
          Kaeufer bewerter;
          do {
            bewerter = kaeuferList.get(getRandomNumberInRange(0, kaeuferList.size() - 1));
          } while (bewerter.equals(buyer));

          kaeuferbewertung.setBewerter(bewerter);
          kaeuferbewertung.setErstellungsDatum(DateUtils.addDays(REFERENCE_DATE, -1 * getRandomNumberInRange(1, 10)));
          kaeuferbewertung.setKommentar(StringUtils.join(faker.lorem().words(5), " "));
          kaeuferbewertung.setWert(new BigDecimal(getRandomNumberInRange(0, 5)));
          kaeuferbewertung.setKaeufer(buyer);

          kaeuferbewertungList.add(kaeuferbewertung);
        }
      }
    }

    final ArrayList<Auktionsbewertung> auktionsbewertungList = new ArrayList<>();
    for (Auktion auktion : auktionList) {
      if (getRandomNumberInRange(1, 10) > 3) {
        for (int i = 0; i < getRandomNumberInRange(1, 100); i++) {
          final Auktionsbewertung auktionsbewertung = new Auktionsbewertung();
          Kaeufer bewerter;
          do {
            bewerter = kaeuferList.get(getRandomNumberInRange(0, kaeuferList.size() - 1));
          } while (bewerter.equals(auktion.getAnbieter().getKaeufer()));

          auktionsbewertung.setBewerter(bewerter);
          auktionsbewertung.setErstellungsDatum(DateUtils.addDays(REFERENCE_DATE, -1 * getRandomNumberInRange(1, 10)));
          auktionsbewertung.setKommentar(StringUtils.join(faker.lorem().words(5), " "));
          auktionsbewertung.setWert(new BigDecimal(getRandomNumberInRange(0, 5)));
          auktionsbewertung.setAuktion(auktion);

          auktionsbewertungList.add(auktionsbewertung);
        }
      }
    }

    final RatingData ratingData = new RatingData();
    ratingData.auktionsbewertungList = auktionsbewertungList;
    ratingData.kaeuferbewertungList = kaeuferbewertungList;
    ratingData.verkaeuferbewertungList = verkaeuferbewertungList;

    return ratingData;
  }

  public class RatingData {
    public List<Verkaeuferbewertung> verkaeuferbewertungList;
    public List<Kaeuferbewertung> kaeuferbewertungList;
    public List<Auktionsbewertung> auktionsbewertungList;

  }
}
