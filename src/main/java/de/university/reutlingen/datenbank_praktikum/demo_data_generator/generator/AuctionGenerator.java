package de.university.reutlingen.datenbank_praktikum.demo_data_generator.generator;

import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.util.Helper.getRandomNumberInRange;
import static de.university.reutlingen.datenbank_praktikum.demo_data_generator.DemoDataGeneratorApplication.REFERENCE_DATE;

import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Adresse;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Auktion;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Exemplar;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Gebot;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Rechnung;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Rechnungsposition;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Verkaeufer;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuctionGenerator {

  public AuctionData generateAuctionDate(List<Exemplar> exemplarList,
                                         List<Verkaeufer> verkaeuferList,
                                         List<Kaeufer> kaeuferList,
                                         List<Adresse> adresseList) {

    final Map<Kaeufer, List<Adresse>> kaeuferLieferAdressList = adresseList
            .stream()
            .filter(adresse -> adresse.getArt().equals(Adresse.Art.LIEFERADRESSE))
            .collect(Collectors.groupingBy(Adresse::getKaeufer));

    final Map<Kaeufer, List<Adresse>> kaeuferRechnungsAdressList = adresseList
            .stream()
            .filter(adresse -> adresse.getArt().equals(Adresse.Art.RECHNUNGSADRESSE))
            .collect(Collectors.groupingBy(Adresse::getKaeufer));

    List<Auktion> auktionList = new ArrayList<>();
    List<Gebot> gebotList = new ArrayList<>();

    Map<Auktion, Gebot> auktionHighestBid = new HashMap<>();
    for (Exemplar exemplar : exemplarList) {
      final Auktion auktion = new Auktion();
      auktion.setStartZeitPunkt(DateUtils.addDays(REFERENCE_DATE, -1 * getRandomNumberInRange(10, 100)));
      auktion.setEndZeitPunkt(DateUtils.addHours(REFERENCE_DATE, -1 * getRandomNumberInRange(10, 100)));
      auktion.setAktiviert(getRandomNumberInRange(1, 10) > 2);

      auktion.setStartpreis(new BigDecimal(getRandomNumberInRange(1, 100)));
      auktion.setWaehrung("EUR");
      auktion.setMindestpreis(auktion.getStartpreis().multiply(new BigDecimal(getRandomNumberInRange(1, 5))));
      auktion.setSofortKaufPreis(auktion.getMindestpreis().divide(new BigDecimal(getRandomNumberInRange(1, 3)), RoundingMode.HALF_UP));

      final int typeRange = getRandomNumberInRange(1, 10);
      if (typeRange > 5) {
        auktion.setArt(Auktion.Art.AUKTION.getRepresentation());
      } else if (typeRange > 2) {
        auktion.setArt(Auktion.Art.SOFORT_KAUF.getRepresentation());
      } else {
        auktion.setArt(Auktion.Art.GEMISCHT.getRepresentation());
      }

      final int versandArtRange = getRandomNumberInRange(1, 10);
      if (versandArtRange > 5) {
        auktion.setVersandArt(Auktion.VersandArt.POST.getRepresentation());
      } else if (versandArtRange > 2) {
        auktion.setVersandArt(Auktion.VersandArt.ABHOLUNG.getRepresentation());
      } else {
        auktion.setVersandArt(Auktion.VersandArt.SPEDITION.getRepresentation());
      }

      auktion.setVersandKosten(auktion.getStartpreis().multiply(new BigDecimal("0.1")));

      auktion.setAnbieter(verkaeuferList.get(getRandomNumberInRange(0, verkaeuferList.size() - 1)));
      auktion.setExemplar(exemplar);

      LinkedList<Gebot> geboteProAuktion = new LinkedList<>();
      for (int i = 0; i < getRandomNumberInRange(10, 40); i++) {
        final Gebot gebot = new Gebot();
        gebot.setAuktion(auktion);
        gebot.setWaehrung("EUR");
        gebot.setBieter(kaeuferList.get(getRandomNumberInRange(0, kaeuferList.size() - 1)));

        if (!geboteProAuktion.isEmpty()) {
          gebot.setHoehe(geboteProAuktion.getLast().getHoehe().add(new BigDecimal(getRandomNumberInRange(5, 10))));
          do {
            gebot.setZeitstempel(DateUtils.addHours(geboteProAuktion.getLast().getZeitstempel(), getRandomNumberInRange(5, 10)));
          } while (gebot.getZeitstempel().before(geboteProAuktion.getLast().getZeitstempel()));
        } else {
          gebot.setHoehe(auktion.getStartpreis().add(new BigDecimal(getRandomNumberInRange(5, 10))));
          do {
            gebot.setZeitstempel(DateUtils.addHours(auktion.getStartZeitPunkt(), getRandomNumberInRange(5, 10)));
          } while (gebot.getZeitstempel().after(auktion.getEndZeitPunkt()));
        }

        geboteProAuktion.addLast(gebot);
      }

      final Gebot maxBid = geboteProAuktion
              .stream()
              .max(Comparator.comparing(Gebot::getHoehe))
              .orElseThrow(() -> new IllegalStateException("Could not find a max bid"));
      auktion.setKaeufer(maxBid.getBieter());
      auktion.setProdukt(auktion.getExemplar().getProdukt());

      auktionList.add(auktion);
      gebotList.addAll(geboteProAuktion);

      auktionHighestBid.put(auktion, maxBid);
    }

    final List<Rechnung> rechnungList = new ArrayList<>();
    final List<Rechnungsposition> rechnungspositionList = new ArrayList<>();

    Map<Rechnungsposition, Auktion> rechnungspositionAuktionMap = new HashMap<>();
    Map<Kaeufer, List<Rechnungsposition>> kaeuferRechnungsMap = new HashMap<>();
    for (Auktion auktion : auktionList) {
      final Rechnungsposition rechnungsposition = new Rechnungsposition();
      rechnungsposition.setExemplar(auktion.getExemplar());
      rechnungsposition.setMenge(getRandomNumberInRange(1, 5));
      rechnungsposition.setPreis(auktionHighestBid.get(auktion).getHoehe().multiply(new BigDecimal(rechnungsposition.getMenge())));

      final Kaeufer currentKaeufer = auktion.getKaeufer();
      List<Rechnungsposition> rechnungspositions = kaeuferRechnungsMap.get(currentKaeufer);
      if (rechnungspositions == null) {
        rechnungspositions = new ArrayList<>();
      }
      rechnungspositions.add(rechnungsposition);

      rechnungspositionList.addAll(rechnungspositions);
      kaeuferRechnungsMap.put(currentKaeufer, rechnungspositions);
      rechnungspositionAuktionMap.put(rechnungsposition, auktion);
    }

    int rechnungsNummer = 0;
    for (Map.Entry<Kaeufer, List<Rechnungsposition>> entry : kaeuferRechnungsMap.entrySet()) {

      for (Rechnungsposition position : entry.getValue()) {
        rechnungsNummer += 1;
        final Rechnung rechnung = new Rechnung();
        final Auktion auktion = rechnungspositionAuktionMap.get(position);

        rechnung.setRechnungsDatum(DateUtils.addHours(auktion.getEndZeitPunkt(), 12));
        if (getRandomNumberInRange(1, 10) > 5) {
          rechnung.setZahlungsDatum(
                  DateUtils.addDays(
                          auktion.getEndZeitPunkt(),
                          getRandomNumberInRange(1, 14)
                  )
          );
          if (getRandomNumberInRange(1, 10) > 3) {
            rechnung.setLieferDatum(
                    DateUtils.addDays(
                            rechnung.getZahlungsDatum(),
                            getRandomNumberInRange(1, 14)
                    )
            );
            if (getRandomNumberInRange(1, 10) > 3) {
              rechnung.setUeberweisungsDatum(
                      DateUtils.addDays(
                              rechnung.getZahlungsDatum(),
                              getRandomNumberInRange(1, 14)
                      )
              );
            }
          }
        }
        rechnung.setRechnungsNummer(new BigDecimal(rechnungsNummer));

        final List<Adresse> lieferAdressListe = kaeuferLieferAdressList.get(entry.getKey());
        final List<Adresse> rechnungsAdressListe = kaeuferRechnungsAdressList.get(entry.getKey());

        final Adresse lieferAdresse;
        if (lieferAdressListe.size() > 1) {
          lieferAdresse = lieferAdressListe.get(getRandomNumberInRange(0, lieferAdressListe.size() - 1));
        } else {
          lieferAdresse = lieferAdressListe.get(0);
        }
        final Adresse rechnungsAdresse;
        if (!CollectionUtils.isEmpty(rechnungsAdressListe) && getRandomNumberInRange(1, 10) > 7) {
          if (rechnungsAdressListe.size() > 1) {
            rechnungsAdresse = rechnungsAdressListe.get(getRandomNumberInRange(0, rechnungsAdressListe.size() - 1));
          } else {
            rechnungsAdresse = rechnungsAdressListe.get(0);
          }
        } else {
          rechnungsAdresse = lieferAdresse;
        }

        rechnung.setLieferAdresse(lieferAdresse);
        rechnung.setRechnungsAdresse(rechnungsAdresse);
        rechnung.setVerkaeuferAdresse(auktion.getAnbieter().getAdresse());

        position.setRechnung(rechnung);
        rechnungList.add(rechnung);
      }

    }

    final AuctionData auctionData = new AuctionData();
    auctionData.auktionList = auktionList;
    auctionData.gebotList = gebotList;
    auctionData.rechnungList = rechnungList;
    auctionData.rechnungspositionList = rechnungspositionList;

    return auctionData;
  }

  public class AuctionData {
    public List<Auktion> auktionList;
    public List<Gebot> gebotList;
    public List<Rechnung> rechnungList;
    public List<Rechnungsposition> rechnungspositionList;

  }
}
