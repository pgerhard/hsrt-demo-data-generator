package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.DemoDataGeneratorApplication.REFERENCE_DATE;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Zahlungsmittel;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArtAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArten;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelAttribut;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodGenerator {

  public static final String BIC = "BIC";
  public static final String IBAN = "IBAN";
  public static final String KONTOINHABER = "Kontoinhaber";
  public static final String NAME_AUF_DER_KARTE = "Name auf der Karte";
  public static final String KARTENNUMMER = "Kartennummer";
  public static final String ABLAUFDATUM = "Ablaufdatum";
  public static final String VORNAME = "Vorname";
  public static final String NACHNAME = "Nachname";
  public static final String GEBURTSDATUM = "Geburtsdatum";
  public static final String E_MAIL = "E-Mail";

  @Autowired
  private Faker faker;

  public static ZahlungsmittelArt getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten type,
                                                       List<ZahlungsmittelArt> types) {
    return types
            .stream()
            .filter(zahlungsmittelArt -> zahlungsmittelArt.getName().equals(type.getRepresentation()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Didnt find an entity for the type"));
  }

  public static ZahlungsmittelArtAttribut getPaymentMethodTypeAttribute(String name,
                                                                        List<ZahlungsmittelArtAttribut> attributs) {
    return attributs
            .stream()
            .filter(attribut -> attribut.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Didn't find an entity for the attribute"));
  }

  /**
   * Rechnung - D.o.B, Vorname, Nachname
   * Überweisung - None
   * Paypal - E-Mail
   * Kreditkarte - Card holder name, Card number, Expiration Date
   * SEPA-Lastschrift - BIC, IBAN, Bank Name
   *
   * @return
   */
  public List<ZahlungsmittelArtAttribut> generatPaymentTypeAttributes(List<ZahlungsmittelArt> paymentTypes) {

    List<ZahlungsmittelArtAttribut> attributs = new ArrayList<>();

    final ZahlungsmittelArtAttribut bicAttribut = new ZahlungsmittelArtAttribut();
    bicAttribut.setName(BIC);
    bicAttribut.setMuster("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)");
    attributs.add(bicAttribut);
    addToType(bicAttribut, ZahlungsmittelArt.ZahlungsmittelArten.SEPA, paymentTypes);

    final ZahlungsmittelArtAttribut ibanAttribut = new ZahlungsmittelArtAttribut();
    ibanAttribut.setName(IBAN);
    ibanAttribut.setMuster("([A-Z]{2})\\s*\\t*(\\d\\d)\\s*\\t*(\\d\\d\\d\\d)\\s*\\t*(\\d\\d\\d\\d)\\s*\\t*(\\d\\d)\\s*\\t*(\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d)");
    attributs.add(ibanAttribut);
    addToType(ibanAttribut, ZahlungsmittelArt.ZahlungsmittelArten.SEPA, paymentTypes);

    final ZahlungsmittelArtAttribut accountOwnerAttribut = new ZahlungsmittelArtAttribut();
    accountOwnerAttribut.setName(KONTOINHABER);
    accountOwnerAttribut.setMuster("[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*");
    attributs.add(accountOwnerAttribut);
    addToType(accountOwnerAttribut, ZahlungsmittelArt.ZahlungsmittelArten.SEPA, paymentTypes);

    final ZahlungsmittelArtAttribut cardHolderNameAttribut = new ZahlungsmittelArtAttribut();
    cardHolderNameAttribut.setName(NAME_AUF_DER_KARTE);
    cardHolderNameAttribut.setMuster("[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*");
    attributs.add(cardHolderNameAttribut);
    addToType(cardHolderNameAttribut, ZahlungsmittelArt.ZahlungsmittelArten.CREDIT_CARD, paymentTypes);

    final ZahlungsmittelArtAttribut cardNumberAttribut = new ZahlungsmittelArtAttribut();
    cardNumberAttribut.setName(KARTENNUMMER);
    cardNumberAttribut.setMuster("(^4[0-9]{12}(?:[0-9]{3})?$)|(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$)|(3[47][0-9]{13})|(^3(?:0[0-5]|[68][0-9])[0-9]{11}$)|(^6(?:011|5[0-9]{2})[0-9]{12}$)|(^(?:2131|1800|35\\d{3})\\d{11}$)");
    attributs.add(cardNumberAttribut);
    addToType(cardNumberAttribut, ZahlungsmittelArt.ZahlungsmittelArten.CREDIT_CARD, paymentTypes);

    final ZahlungsmittelArtAttribut cardExpirationDateAttribut = new ZahlungsmittelArtAttribut();
    cardExpirationDateAttribut.setName(ABLAUFDATUM);
    cardExpirationDateAttribut.setMuster("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)");
    attributs.add(cardExpirationDateAttribut);
    addToType(cardExpirationDateAttribut, ZahlungsmittelArt.ZahlungsmittelArten.CREDIT_CARD, paymentTypes);

    final ZahlungsmittelArtAttribut recipientFirstNameAttribut = new ZahlungsmittelArtAttribut();
    recipientFirstNameAttribut.setName(VORNAME);
    recipientFirstNameAttribut.setMuster("[A-Z][a-zA-Z]*");
    attributs.add(recipientFirstNameAttribut);
    addToType(recipientFirstNameAttribut, ZahlungsmittelArt.ZahlungsmittelArten.RECHNUNG, paymentTypes);

    final ZahlungsmittelArtAttribut recipientLastNameAttribut = new ZahlungsmittelArtAttribut();
    recipientLastNameAttribut.setName(NACHNAME);
    recipientLastNameAttribut.setMuster("[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*");
    attributs.add(recipientLastNameAttribut);
    addToType(recipientLastNameAttribut, ZahlungsmittelArt.ZahlungsmittelArten.RECHNUNG, paymentTypes);

    final ZahlungsmittelArtAttribut dobAttribut = new ZahlungsmittelArtAttribut();
    dobAttribut.setName(GEBURTSDATUM);
    dobAttribut.setMuster("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)");
    attributs.add(dobAttribut);
    addToType(dobAttribut, ZahlungsmittelArt.ZahlungsmittelArten.RECHNUNG, paymentTypes);

    final ZahlungsmittelArtAttribut emailAttribut = new ZahlungsmittelArtAttribut();
    emailAttribut.setName(E_MAIL);
    emailAttribut.setMuster("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    attributs.add(emailAttribut);
    addToType(emailAttribut, ZahlungsmittelArt.ZahlungsmittelArten.PAYPAL, paymentTypes);

    return attributs;
  }

  public UserPaymentInformation generatePaymentMethods(List<Kaeufer> kaeuferList,
                                                       List<ZahlungsmittelArt> paymentMethodTypes,
                                                       List<ZahlungsmittelArtAttribut> paymentMethodTypeAttribute) {

    final UserPaymentInformation userPaymentInformation = new UserPaymentInformation();

    final List<Zahlungsmittel> zahlungsmittels = new ArrayList<>();
    final List<ZahlungsmittelAttribut> zahlungsmittelAttribute = new ArrayList<>();

    for (Kaeufer kaeufer : kaeuferList) {
      final int numberOfPaymentMethods = DemoAddressService.getRandomNumberInRange(1, 5);
      for (int i = 0; i < numberOfPaymentMethods; i++) {
        final int numberOfPaymentMethodType = DemoAddressService.getRandomNumberInRange(0, 4);
        final ZahlungsmittelArt.ZahlungsmittelArten paymentMethodType = ZahlungsmittelArt.ZahlungsmittelArten.values()[numberOfPaymentMethodType];

        final Zahlungsmittel zahlungsmittel = new Zahlungsmittel();
        zahlungsmittel.setKaufer(kaeufer);

        String paymentMethodName;
        switch (paymentMethodType) {
          case SEPA:
            paymentMethodName = String.format("Sepa %s %s", kaeufer.getVorname(), kaeufer.getNachname());
            zahlungsmittel.setName(paymentMethodName);
            zahlungsmittel.setZahlungsmittelart(getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.SEPA, paymentMethodTypes));

            final ZahlungsmittelAttribut bic = new ZahlungsmittelAttribut();
            bic.setWert(faker.finance().bic());
            bic.setZahlungsmittel(zahlungsmittel);
            bic.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(BIC, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(bic);

            final ZahlungsmittelAttribut iban = new ZahlungsmittelAttribut();
            iban.setWert(faker.finance().iban("DE"));
            iban.setZahlungsmittel(zahlungsmittel);
            iban.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(IBAN, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(iban);
            break;
          case RECHNUNG:
            paymentMethodName = String.format("Rechnung %s %s", kaeufer.getVorname(), kaeufer.getNachname());
            zahlungsmittel.setName(paymentMethodName);
            zahlungsmittel.setZahlungsmittelart(getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.RECHNUNG, paymentMethodTypes));

            final ZahlungsmittelAttribut vorname = new ZahlungsmittelAttribut();
            vorname.setWert(kaeufer.getVorname());
            vorname.setZahlungsmittel(zahlungsmittel);
            vorname.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(VORNAME, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(vorname);

            final ZahlungsmittelAttribut nachname = new ZahlungsmittelAttribut();
            nachname.setWert(kaeufer.getVorname());
            nachname.setZahlungsmittel(zahlungsmittel);
            nachname.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(NACHNAME, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(nachname);

            final ZahlungsmittelAttribut dob = new ZahlungsmittelAttribut();
            dob.setWert(faker.date().birthday().toString());
            dob.setZahlungsmittel(zahlungsmittel);
            dob.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(GEBURTSDATUM, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(dob);
            break;
          case CREDIT_CARD:
            paymentMethodName = String.format("Kredit Karte %s %s", kaeufer.getVorname(), kaeufer.getNachname());
            zahlungsmittel.setName(paymentMethodName);
            zahlungsmittel.setZahlungsmittelart(getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.CREDIT_CARD, paymentMethodTypes));

            final ZahlungsmittelAttribut nameOnCard = new ZahlungsmittelAttribut();
            nameOnCard.setWert(String.format("%s %s", kaeufer.getVorname(), kaeufer.getNachname()));
            nameOnCard.setZahlungsmittel(zahlungsmittel);
            nameOnCard.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(NAME_AUF_DER_KARTE, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(nameOnCard);

            final ZahlungsmittelAttribut cardNumber = new ZahlungsmittelAttribut();
            cardNumber.setWert(faker.business().creditCardNumber());
            cardNumber.setZahlungsmittel(zahlungsmittel);
            cardNumber.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(KARTENNUMMER, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(cardNumber);

            final ZahlungsmittelAttribut expirationDate = new ZahlungsmittelAttribut();
            expirationDate.setWert(faker.date().between(
                    DateUtils.addYears(REFERENCE_DATE, 1),
                    DateUtils.addYears(REFERENCE_DATE, 4)
            ).toString());
            expirationDate.setZahlungsmittel(zahlungsmittel);
            expirationDate.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(ABLAUFDATUM, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(expirationDate);
            break;
          case PAYPAL:
            paymentMethodName = String.format("Paypal %s %s", kaeufer.getVorname(), kaeufer.getNachname());
            zahlungsmittel.setName(paymentMethodName);
            zahlungsmittel.setZahlungsmittelart(getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.PAYPAL, paymentMethodTypes));

            final ZahlungsmittelAttribut email = new ZahlungsmittelAttribut();
            email.setWert(kaeufer.getEmail());
            email.setZahlungsmittel(zahlungsmittel);
            email.setZahlungsmittelArtAttribute(getPaymentMethodTypeAttribute(E_MAIL, paymentMethodTypeAttribute));
            zahlungsmittelAttribute.add(email);
            break;
          case TRANSFER:
            paymentMethodName = String.format("Überweisung %s %s", kaeufer.getVorname(), kaeufer.getNachname());
            zahlungsmittel.setName(paymentMethodName);
            zahlungsmittel.setZahlungsmittelart(getPaymentMethodType(ZahlungsmittelArt.ZahlungsmittelArten.TRANSFER, paymentMethodTypes));
            break;
          default:
            break;
        }

        zahlungsmittels.add(zahlungsmittel);
      }
    }
    userPaymentInformation.zahlungsmittels = zahlungsmittels;
    userPaymentInformation.zahlungsmittelAttributs = zahlungsmittelAttribute;
    return userPaymentInformation;
  }

  public List<ZahlungsmittelArt> generatePaymentMethodTypes() {
    return Arrays.stream(ZahlungsmittelArten.values())
            .map(value -> {
              final ZahlungsmittelArt zahlungsmittelArt = new ZahlungsmittelArt();
              zahlungsmittelArt.setName(value.getRepresentation());
              return zahlungsmittelArt;
            })
            .collect(Collectors.toList());
  }

  private void addToType(ZahlungsmittelArtAttribut attribut, ZahlungsmittelArt.ZahlungsmittelArten type,
                         List<ZahlungsmittelArt> paymentTypes) {
    paymentTypes
            .stream()
            .filter(paymentType -> paymentType.getName().equals(type.getRepresentation()))
            .forEach(paymentType -> {
              if (CollectionUtils.isEmpty(attribut.getArten())) {
                final List<ZahlungsmittelArt> arten = new ArrayList<>();
                arten.add(paymentType);
                attribut.setArten(arten);
              } else {
                attribut.getArten().add(paymentType);
              }

            });
  }

  public class UserPaymentInformation {
    public List<Zahlungsmittel> zahlungsmittels;
    public List<ZahlungsmittelAttribut> zahlungsmittelAttributs;

  }
}
