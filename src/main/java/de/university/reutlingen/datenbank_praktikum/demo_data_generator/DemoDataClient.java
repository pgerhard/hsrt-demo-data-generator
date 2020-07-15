package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.AdressGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.AuctionGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.KaeuferGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.PaymentMethodGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.ProductGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.RatingGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator.SellerGenerator;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArt;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArtAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.AdresseRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.AuktionRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.AuktionsbewertungRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ExemplarRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.GebotRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.KaeuferRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.KaeuferbewertungRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ProduktRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ProduktkategorieRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ProduktmerkmalRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ProduktvorlageRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ProduktvorlagemerkmalRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.RechnungRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.RechnungspositionRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.SchlagwortRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.VerkaeuferRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.VerkaeuferbewertungRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelArtAttributRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelArtRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelAttributRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.transaction.Transactional;

@RestController
public class DemoDataClient {

  @Autowired
  private KaeuferGenerator kaeuferGenerator;

  @Autowired
  private AdressGenerator adressGenerator;

  @Autowired
  private PaymentMethodGenerator paymentMethodGenerator;

  @Autowired
  private SellerGenerator sellerGenerator;

  @Autowired
  private AuktionsbewertungRepository auktionsbewertungRepository;

  @Autowired
  private KaeuferbewertungRepository kaeuferbewertungRepository;

  @Autowired
  private VerkaeuferbewertungRepository verkaeuferbewertungRepository;

  @Autowired
  private KaeuferRepository kaeuferRepository;

  @Autowired
  private AdresseRepository adresseRepository;

  @Autowired
  private AuktionRepository auktionRepository;

  @Autowired
  private GebotRepository gebotRepository;

  @Autowired
  private RechnungRepository rechnungRepository;

  @Autowired
  private RechnungspositionRepository rechnungspositionRepository;

  @Autowired
  private ZahlungsmittelArtRepository zahlungsmittelArtRepository;

  @Autowired
  private ZahlungsmittelArtAttributRepository zahlungsmittelArtAttributRepository;

  @Autowired
  private ZahlungsmittelRepository zahlungsmittelRepository;

  @Autowired
  private ZahlungsmittelAttributRepository zahlungsmittelAttributRepository;

  @Autowired
  private SchlagwortRepository schlagwortRepository;

  @Autowired
  private ProduktkategorieRepository produktkategorieRepository;

  @Autowired
  private ProduktRepository produktRepository;

  @Autowired
  private ProduktmerkmalRepository produktmerkmalRepository;

  @Autowired
  private ProduktvorlagemerkmalRepository produktvorlagemerkmalRepository;

  @Autowired
  private ProduktvorlageRepository produktvorlageRepository;

  @Autowired
  private ExemplarRepository exemplarRepository;

  @Autowired
  private VerkaeuferRepository verkaeuferRepository;

  @Autowired
  private ProductGenerator productGenerator;

  @Autowired
  private AuctionGenerator auctionGenerator;

  @Autowired
  private RatingGenerator ratingGenerator;

  @PostMapping("/kaeufer")
  public List<Kaeufer> generateDemoData(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final List<Kaeufer> kaeuferList = kaeuferGenerator.generateKaeufer();

    if (!dryRun) {
      return saveKaeufer(kaeuferList);
    } else {
      System.out.println("Dry run");
      return kaeuferList;
    }
  }

  @PostMapping("/adressen")
  public List<Adresse> generateAdresses(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final List<Adresse> adresseList = adressGenerator.generateAdressees(kaeuferRepository.findAll());
    if (!dryRun) {
      return saveAdressen(adresseList);
    } else {
      System.out.println("Dry run");
      return adresseList;
    }
  }

  @PostMapping("/zahlungsmittelArten")
  public List<ZahlungsmittelArt> generatePaymentTypes(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final List<ZahlungsmittelArt> paymentTypes = paymentMethodGenerator.generatePaymentMethodTypes();
    if (!dryRun) {
      return savePaymentTypes(paymentTypes);
    } else {
      System.out.println("Dry run");
      return paymentTypes);
    }
  }

  @PostMapping("/zahlungsmittelArtAttribut")
  public List<ZahlungsmittelArtAttribut> generatePaymentTypeAttributes(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final List<ZahlungsmittelArtAttribut> artAttributList = paymentMethodGenerator.generatPaymentTypeAttributes(zahlungsmittelArtRepository.findAll());

    if (!dryRun) {
      return savePaymentTypeAttributes(artAttributList);
    } else {
      System.out.println("Dry run");
      return artAttributList;
    }
  }

  @PostMapping("/zahlungsmittel")
  public PaymentMethodGenerator.UserPaymentInformation generatePaymentMethods(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final PaymentMethodGenerator.UserPaymentInformation paymentMethods = paymentMethodGenerator.generatePaymentMethods(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll());

    if (!dryRun) {
      return store(paymentMethods);
    } else {
      System.out.println("Dry run");
      return paymentMethods;
    }
  }

  @PostMapping("/verkaeufer")
  public SellerGenerator.SellerData generateSellerData(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final SellerGenerator.SellerData sellerData = sellerGenerator.generateSellerData(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll());

    if (!dryRun) {
      return store(sellerData);
    } else {
      System.out.println("Dry run");
      return sellerData;
    }
  }

  @PostMapping("/produkte")
  public ProductGenerator.ProductData generateProductData(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final ProductGenerator.ProductData productData = productGenerator.generateProductData();

    if (!dryRun) {
      return store(productData);
    } else {
      System.out.println("Dry run");
      return productData;
    }
  }

  @PostMapping("/auktionen")
  public AuctionGenerator.AuctionData generateAuctionDate(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final AuctionGenerator.AuctionData auctionData = auctionGenerator.generateAuctionDate(
            exemplarRepository.findAll(),
            verkaeuferRepository.findAll(),
            kaeuferRepository.findAll(),
            adresseRepository.findAll()
    );

    if (!dryRun) {
      return store(auctionData);
    } else {
      System.out.println("Dry run");
      return auctionData;
    }
  }

  @PostMapping("/bewertungen")
  public RatingGenerator.RatingData generateRatingDate(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final RatingGenerator.RatingData ratingData = ratingGenerator.generateRatingData(
            verkaeuferRepository.findAll(),
            kaeuferRepository.findAll(),
            auktionRepository.findAll()
    );
    if (!dryRun) {
      return store(ratingData);
    } else {
      System.out.println("Dry run");
      return ratingData;
    }
  }

  @Transactional
  public List<Kaeufer> saveKaeufer(List<Kaeufer> buyers) {
    return kaeuferRepository.saveAll(buyers);
  }

  @Transactional
  public List<Adresse> saveAdressen(List<Adresse> adresses) {
    return adresseRepository.saveAll(adresses);
  }

  @Transactional
  public List<ZahlungsmittelArt> savePaymentTypes(List<ZahlungsmittelArt> paymentTypes) {
    return zahlungsmittelArtRepository.saveAll(paymentTypes);
  }

  @Transactional
  public List<ZahlungsmittelArtAttribut> savePaymentTypeAttributes(List<ZahlungsmittelArtAttribut> toSave) {
    zahlungsmittelArtAttributRepository.saveAll(toSave);
    return zahlungsmittelArtAttributRepository.findAll();
  }

  @Transactional
  public PaymentMethodGenerator.UserPaymentInformation store(PaymentMethodGenerator.UserPaymentInformation userPaymentInformation) {
    zahlungsmittelRepository.saveAll(userPaymentInformation.zahlungsmittels);
    zahlungsmittelAttributRepository.saveAll(userPaymentInformation.zahlungsmittelAttributs);
    return userPaymentInformation;
  }

  @Transactional
  public SellerGenerator.SellerData store(SellerGenerator.SellerData data) {
    adresseRepository.saveAll(data.sellerAdresses);
    zahlungsmittelRepository.saveAll(data.zahlungsmittels);
    zahlungsmittelAttributRepository.saveAll(data.attribute);
    verkaeuferRepository.saveAll(data.sellers);
    return data;
  }

  @Transactional
  public ProductGenerator.ProductData store(ProductGenerator.ProductData data) {
    schlagwortRepository.saveAll(data.schlagwortList);
    produktkategorieRepository.saveAll(data.produktkategorieList);
    produktvorlageRepository.saveAll(data.produktvorlageList);
    produktvorlagemerkmalRepository.saveAll(data.produktvorlagemerkmalList);
    produktRepository.saveAll(data.produktList);
    produktmerkmalRepository.saveAll(data.produktmerkmalList);
    exemplarRepository.saveAll(data.exemplarList);
    return data;
  }

  @Transactional
  public AuctionGenerator.AuctionData store(AuctionGenerator.AuctionData data) {
    auktionRepository.saveAll(data.auktionList);
    gebotRepository.saveAll(data.gebotList);
    rechnungRepository.saveAll(data.rechnungList);
    rechnungspositionRepository.saveAll(data.rechnungspositionList);
    return data;
  }

  @Transactional
  public RatingGenerator.RatingData store(RatingGenerator.RatingData data) {
    verkaeuferbewertungRepository.saveAll(data.verkaeuferbewertungList);
    kaeuferbewertungRepository.saveAll(data.kaeuferbewertungList);
    auktionsbewertungRepository.saveAll(data.auktionsbewertungList);
    return data;
  }
}
