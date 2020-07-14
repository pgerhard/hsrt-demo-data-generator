package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.transaction.Transactional;

@RestController
public class DemoDataClient {

  private final TransactionTemplate txTemplate;
  @Autowired
  private DemoDataService demoDataService;
  @Autowired
  private DemoAddressService demoAddressService;
  @Autowired
  private PaymentMethodGenerator paymentMethodGenerator;
  @Autowired
  private SellerGenerator sellerGenerator;
  @Autowired
  private PlatformTransactionManager transactionManager;
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

  public DemoDataClient() {
    this.txTemplate = new TransactionTemplate(transactionManager);
  }

  @PostMapping("/kaeufer")
  public List<Kaeufer> generateDemoData() {
    return saveKaeufer(demoDataService.generateKaeufer());
  }

  @PostMapping("/adressen")
  public List<Adresse> generateAdresses() {
    return saveAdressen(demoAddressService.generateAdressees(kaeuferRepository.findAll()));
  }

  @PostMapping("/zahlungsmittelArten")
  public List<ZahlungsmittelArt> generatePaymentTypes() {
    return savePaymentTypes(paymentMethodGenerator.generatePaymentMethodTypes());
  }

  @PostMapping("/zahlungsmittelArtAttribut")
  public List<ZahlungsmittelArtAttribut> generatePaymentTypeAttributes() {
    return savePaymentTypeAttributes(paymentMethodGenerator.generatPaymentTypeAttributes(zahlungsmittelArtRepository.findAll()));
  }

  @PostMapping("/zahlungsmittel")
  public PaymentMethodGenerator.UserPaymentInformation generatePaymentMethods() {
    return store(paymentMethodGenerator.generatePaymentMethods(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll()));
  }

  @PostMapping("/verkaeufer")
  public SellerGenerator.SellerData generateSellerData() {
    return store(sellerGenerator.generateSellerData(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll()));
  }

  @PostMapping("/produkte")
  public ProductGenerator.ProductData generateProductData(
          @RequestParam(name = "dry-run", required = false, defaultValue = "TRUE") Boolean dryRun) {
    final ProductGenerator.ProductData productData = productGenerator.generateProductData();

    if (!dryRun) {
      return store(productData);
    } else {
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
