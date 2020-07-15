package de.university.reutlingen.datenbank_praktikum.demo_data_generator.util;

import java.util.Random;

public final class Helper {

  /**
   * Generate a random number inside the predefined range.
   *
   * @param min of the range
   * @param max of the range
   * @return the random number
   */
  public static int getRandomNumberInRange(int min, int max) {

    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
}
