package de.university.reutlingen.datenbank_praktikum.demo_data_generator;

import com.github.javafaker.Faker;
import de.university.reutlingen.datenbank_praktikum.demo_data_generator.model.Kaeufer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

@Component
public class DemoDataService {

  private static final BiFunction<String, String, String> TO_EMAIL = (firstname, lastname) -> String.format("%s.%s@example.com", firstname.toLowerCase(), lastname.toLowerCase());
  private static final BiFunction<String, String, String> TO_USERNAME = (firstname, lastname) -> firstname.toLowerCase().charAt(0) + lastname.toLowerCase();
  private final Set<String> usedEmails = new HashSet<>();
  private final Set<String> usedUsernames = new HashSet<>();
  @Autowired
  private Faker faker;

  public List<Kaeufer> generateKaeufer() {

    final List<Kaeufer> kaeufers = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      String firstname;
      String lastname;
      String email;
      String username;
      do {
        firstname = faker.name().firstName();
        lastname = faker.name().lastName();
        email = TO_EMAIL.apply(firstname, lastname);
        username = TO_USERNAME.apply(firstname, lastname);
      } while (usedEmails.contains(email) || usedUsernames.contains(username));
      usedEmails.add(email);
      usedUsernames.add(username);

      final Kaeufer kaeufer = new Kaeufer();
      kaeufer.setVorname(firstname);
      kaeufer.setNachname(lastname);
      kaeufer.setEmail(email);
      kaeufer.setUsername(username);
      kaeufer.setHandynummer(faker.phoneNumber().cellPhone());
      kaeufer.setTelefonnummer(faker.phoneNumber().phoneNumber());
      kaeufers.add(kaeufer);
    }

    return kaeufers;
  }

}
