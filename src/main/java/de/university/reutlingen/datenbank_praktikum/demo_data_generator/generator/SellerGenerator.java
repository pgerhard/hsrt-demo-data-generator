package de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator;

import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.PaymentMethodGenerator.E_MAIL;
import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.PaymentMethodGenerator.getPaymentMethodTypeAttribute;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Verkaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Zahlungsmittel;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArt;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArtAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerGenerator {

  @Autowired
  private Faker faker;

  public SellerData generateSellerData(List<Kaeufer> buyers,
                                       List<ZahlungsmittelArt> paymentMethodTypes,
                                       List<ZahlungsmittelArtAttribut> paymentMethodTypeAttribute) {

    List<String> usedEmails = new ArrayList<>();

    List<Verkaeufer> sellers = new ArrayList<>();
    List<Adresse> adresses = new ArrayList<>();
    List<Zahlungsmittel> paymentMethods = new ArrayList<>();
    List<ZahlungsmittelAttribut> paymentMethodAttributes = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      Kaeufer buyer;
      do {
        final int buyerIndex = Helper.getRandomNumberInRange(0, buyers.size() - 1);
        buyer = buyers.get(buyerIndex);
      } while (usedEmails.contains(buyer.getEmail()));
      usedEmails.add(buyer.getEmail());

      final Adresse adresse = new Adresse();
      adresse.setVorname(buyer.getVorname());
      adresse.setNachname(buyer.getNachname());
      adresse.setStrasse(faker.address().streetName());
      adresse.setHausnummer(Integer.valueOf(faker.address().streetAddressNumber()));
      adresse.setPlz(faker.address().zipCode());
      adresse.setStadt(faker.address().city());
      adresse.setBundesland(faker.address().state());
      adresse.setLand("Deutschland");
      adresse.setKaeufer(buyer);
      adresse.setArt(Adresse.Art.RECHNUNGSADRESSE);
      adresses.add(adresse);

      final Zahlungsmittel zahlungsmittel = new Zahlungsmittel();
      zahlungsmittel.setKaufer(buyer);
      zahlungsmittel.setZahlungsmittelart(PaymentMethodGenerator.getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.PAYPAL, paymentMethodTypes));
      zahlungsmittel.setName(String.format("Geldeingang Verkäufer %s %s", buyer.getVorname(), buyer.getNachname()));
      paymentMethods.add(zahlungsmittel);

      final ZahlungsmittelAttribut email = new ZahlungsmittelAttribut();
      email.setWert(buyer.getEmail());
      email.setZahlungsmittel(zahlungsmittel);
      email.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(E_MAIL, paymentMethodTypeAttribute));
      paymentMethodAttributes.add(email);

      final Verkaeufer seller = new Verkaeufer();
      seller.setKaeufer(buyer);
      seller.setAdresse(adresse);
      seller.setZahlungsmittel(zahlungsmittel);
      sellers.add(seller);
    }

    final SellerData sellerData = new SellerData();
    sellerData.sellers = sellers;
    sellerData.sellerAdresses = adresses;
    sellerData.zahlungsmittels = paymentMethods;
    sellerData.attribute = paymentMethodAttributes;
    return sellerData;
  }

  public class SellerData {
    public List<Verkaeufer> sellers;
    public List<Adresse> sellerAdresses;
    public List<Zahlungsmittel> zahlungsmittels;
    public List<ZahlungsmittelAttribut> attribute;
  }
}
