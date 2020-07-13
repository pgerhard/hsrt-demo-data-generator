package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelArtAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.ZahlungsmittelAttribut;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.AdresseRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.KaeuferRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.VerkaeuferRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelArtAttributRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelArtRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelAttributRepository;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.repository.jpa.ZahlungsmittelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.transaction.Transactional;

@RestController
public class DemoDataClient {

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
  private KaeuferRepository kaeuferRepository;

  @Autowired
  private AdresseRepository adresseRepository;

  @Autowired
  private ZahlungsmittelArtRepository zahlungsmittelArtRepository;

  @Autowired
  private ZahlungsmittelArtAttributRepository zahlungsmittelArtAttributRepository;

  @Autowired
  private ZahlungsmittelRepository zahlungsmittelRepository;

  @Autowired
  private ZahlungsmittelAttributRepository zahlungsmittelAttributRepository;

  @Autowired
  private VerkaeuferRepository verkaeuferRepository;

  private final TransactionTemplate txTemplate;

  public DemoDataClient() {
    this.txTemplate = new TransactionTemplate(transactionManager);
  }

  @PostMapping("/kaeufer")
  public List<Kaeufer> generateDemoData () {
    return saveKaeufer(demoDataService.generateKaeufer());
  }

  @PostMapping("/adressen")
  public List<Adresse> generateAdresses () {
    return saveAdressen(demoAddressService.generateAdressees(kaeuferRepository.findAll()));
  }

  @PostMapping("/zahlungsmittelArten")
  public List<ZahlungsmittelArt> generatePaymentTypes () {
    return savePaymentTypes(paymentMethodGenerator.generatePaymentMethodTypes());
  }

  @PostMapping("/zahlungsmittelArtAttribut")
  public List<ZahlungsmittelArtAttribut> generatePaymentTypeAttributes(){
    return savePaymentTypeAttributes(paymentMethodGenerator.generatPaymentTypeAttributes(zahlungsmittelArtRepository.findAll()));
  }

  @PostMapping("/zahlungsmittel")
  public PaymentMethodGenerator.UserPaymentInformation generatePaymentMethods(){
    return store(paymentMethodGenerator.generatePaymentMethods(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll()));
  }

  @PostMapping("/verkaeufer")
  public SellerGenerator.SellerData generateSellerData(){
    return store(sellerGenerator.generateSellerData(
            kaeuferRepository.findAll(),
            zahlungsmittelArtRepository.findAll(),
            zahlungsmittelArtAttributRepository.findAll()));
  }

  @Transactional
  public List<Kaeufer> saveKaeufer (List<Kaeufer> buyers){
    return kaeuferRepository.saveAll(buyers);
  }

  @Transactional
  public List<Adresse> saveAdressen(List<Adresse> adresses){
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
  public PaymentMethodGenerator.UserPaymentInformation store(PaymentMethodGenerator.UserPaymentInformation userPaymentInformation){
    zahlungsmittelRepository.saveAll(userPaymentInformation.zahlungsmittels);
    zahlungsmittelAttributRepository.saveAll(userPaymentInformation.zahlungsmittelAttributs);
    return userPaymentInformation;
  }

  @Transactional
  public SellerGenerator.SellerData store (SellerGenerator.SellerData data){
    adresseRepository.saveAll(data.sellerAdresses);
    zahlungsmittelRepository.saveAll(data.zahlungsmittels);
    zahlungsmittelAttributRepository.saveAll(data.attribute);
    verkaeuferRepository.saveAll(data.sellers);
    return data;
  }
}
