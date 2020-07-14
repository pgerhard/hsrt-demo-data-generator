package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Exemplar;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produkt;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktkategorie;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktmerkmal;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktvorlage;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Produktvorlagemerkmal;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Schlagwort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class ProductGenerator {

  @Autowired
  private Faker faker;

  public static <T> List<T> takeRandomItems(List<T> sourceList, int upperBound) {
    final HashSet<T> randomItems = new HashSet<>();
    do {
      final int randomIndex = DemoAddressService.getRandomNumberInRange(0, sourceList.size() - 1);
      randomItems.add(sourceList.get(randomIndex));
    } while (randomItems.size() < upperBound);

    return new ArrayList<>(randomItems);
  }

  public ProductData generateProductData() {

    final List<Schlagwort> kategorieSchlagwortList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      final Schlagwort schlagwort = new Schlagwort();
      schlagwort.setWert(faker.lorem().word());
      schlagwort.setVordefiniert(Boolean.TRUE);
      kategorieSchlagwortList.add(schlagwort);
    }

    final List<Schlagwort> produktSchlagwortList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      final Schlagwort schlagwort = new Schlagwort();
      schlagwort.setWert(faker.lorem().word());
      schlagwort.setVordefiniert(Boolean.FALSE);
      produktSchlagwortList.add(schlagwort);
    }

    final List<Produktkategorie> produktkategorieList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      final Produktkategorie allInOneCat = new Produktkategorie();
      allInOneCat.setBezeichnung(faker.commerce().department());
      allInOneCat.setSchlagwortList(takeRandomItems(kategorieSchlagwortList, 5));
      produktkategorieList.add(allInOneCat);
    }

    final List<Produktvorlagemerkmal> produktvorlagemerkmalList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      final Produktvorlagemerkmal herstellerVorlagemerkmal = new Produktvorlagemerkmal();
      herstellerVorlagemerkmal.setName(faker.lorem().word());
      if (DemoAddressService.getRandomNumberInRange(0, 10) > 3) {
        herstellerVorlagemerkmal.setBearbeitbar(Boolean.TRUE);
        herstellerVorlagemerkmal.setStartwert("");
      } else {
        herstellerVorlagemerkmal.setBearbeitbar(Boolean.FALSE);
        herstellerVorlagemerkmal.setStartwert(faker.lorem().word());
      }

      herstellerVorlagemerkmal.setProduktvorlagen(new ArrayList<>());
      produktvorlagemerkmalList.add(herstellerVorlagemerkmal);
    }

    Map<Produktvorlage, List<Produktvorlagemerkmal>> templatesAndAttributes = new HashMap<>();
    final List<Produktvorlage> produktvorlageList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      final Produktvorlage produktvorlage = new Produktvorlage();
      produktvorlage.setName(faker.lorem().word());
      produktvorlage.setProduktkategorie(produktkategorieList.get(
              DemoAddressService.getRandomNumberInRange(0, produktkategorieList.size() - 1)
      ));

      final List<Produktvorlagemerkmal> produktvorlagemerkmale = takeRandomItems(produktvorlagemerkmalList, 5);
      produktvorlagemerkmale.forEach(merkmal -> merkmal.getProduktvorlagen().add(produktvorlage));

      templatesAndAttributes.put(produktvorlage, produktvorlagemerkmale);
      produktvorlageList.add(produktvorlage);
    }

    List<Produkt> produktList = new ArrayList<>();
    List<Produktmerkmal> produktmerkmalList = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      final Produktvorlage produktvorlage = produktvorlageList.get(DemoAddressService.getRandomNumberInRange(0, produktvorlageList.size() - 1));

      final Produkt produkt = new Produkt();
      produkt.setBezeichnung(faker.commerce().productName());
      produkt.setProduktkategorie(produktvorlage.getProduktkategorie());
      produkt.setBeschreibung(
              String.format("Beschreibung von %s in Kategorie %s",
                      produkt.getBezeichnung(),
                      produkt.getProduktkategorie().getBezeichnung()
              ));
      produkt.setSchlagwortList(takeRandomItems(produktSchlagwortList, 5));

      final List<Produktvorlagemerkmal> merkmale = templatesAndAttributes.get(produktvorlage);
      for (Produktvorlagemerkmal merkmal : merkmale) {
        final Produktmerkmal produktmerkmal = new Produktmerkmal();
        produktmerkmal.setWert(faker.lorem().word());
        produktmerkmal.setProdukt(produkt);
        produktmerkmal.setProduktvorlagemerkmal(merkmal);
        produktmerkmalList.add(produktmerkmal);
      }

      produktList.add(produkt);
    }

    List<Exemplar> exemplarList = new ArrayList<>();
    for (Produkt produkt : produktList) {
      final int numberOfExemplare = DemoAddressService.getRandomNumberInRange(1, 10);
      for (int i = 0; i < numberOfExemplare; i++) {
        final Exemplar exemplar = new Exemplar();
        exemplar.setProdukt(produkt);
        exemplar.setSerienNummer(faker.bothify("???###???###???#?#?#?"));
        exemplarList.add(exemplar);
      }
    }

    final ProductData productData = new ProductData();
    final ArrayList<Schlagwort> combinedList = new ArrayList<>();
    combinedList.addAll(kategorieSchlagwortList);
    combinedList.addAll(produktSchlagwortList);

    productData.schlagwortList = combinedList;
    productData.produktkategorieList = produktkategorieList;
    productData.produktvorlageList = produktvorlageList;
    productData.produktvorlagemerkmalList = produktvorlagemerkmalList;
    productData.produktList = produktList;
    productData.produktmerkmalList = produktmerkmalList;
    productData.exemplarList = exemplarList;

    return productData;
  }

  public class ProductData {
    public List<Schlagwort> schlagwortList;
    public List<Produktkategorie> produktkategorieList;
    public List<Produktvorlage> produktvorlageList;
    public List<Produktvorlagemerkmal> produktvorlagemerkmalList;
    public List<Produkt> produktList;
    public List<Produktmerkmal> produktmerkmalList;
    public List<Exemplar> exemplarList;
  }

}
