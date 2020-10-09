package edu.cnm.deepdive.codebreaker.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Handles everything dealing with the secret code for the game. At the start of the game, the
 * program generates the secret code that the user must attempt to guess. For every guess submitted,
 *  the program will check that guess against the secret code and inform the player of the number
 *  of correct and close characters in the guess.
 */
public class Code {

  private final char[] secret;

  /**
   * Generates a new secret code at the start of the game.
   *
   * @param pool Pool of characters that can be chosen to be in the secret code.
   * @param length Number of characters in the secret code.
   * @param rng Source of randomness for generating the code.
   */
  public Code(String pool, int length, Random rng) {
    secret = new char[length];
    for (int i = 0; i < secret.length; i++) {
      secret[i] = pool.charAt(rng.nextInt(pool.length()));
    }
  }

  @Override
  public String toString() {
    return new String(secret);
  }

  /**
   * Handles user guesses for each turn by taking in the user's guess, comparing it to the
   * {@link Code}, and determining how many characters in the guess are correct or close.
   */
  public class Guess {

    private static final String STRING_FORMAT = "{text: \"%s\", correct: %d, close: %d}";

    private final String text;
    private final int correct;
    private final int close;

    /**
     * Determines how many correct and close guesses are in a guess by comparing {@code text} to the
     * secret code.
     *
     * @param text The user's guess.
     */
    public Guess(String text) {
      this.text = text;
      int correct = 0;
      int close = 0;

      Map<Character, Set<Integer>> letterMap = getLetterMap(text);

      char[] work = Arrays.copyOf(secret, secret.length);

      for (int i = 0; i < work.length; i++) {
        char letter = work[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
        if (positions.contains(i)) {
          correct++;
          positions.remove(i);
          work[i] = 0;
        }
      }

      for (char letter : work) {
        if (letter != 0) {
          Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
          if (positions.size() > 0) {
            close++;
            Iterator<Integer> iter = positions.iterator();
            iter.next();
            iter.remove();
          }
        }
      }

      this.correct = correct;
      this.close = close;
    }

    private Map<Character, Set<Integer>> getLetterMap(String text) {
      Map<Character, Set<Integer>> letterMap = new HashMap<>();
      char[] letters = text.toCharArray();
      for (int i = 0; i < letters.length; i++) {
        char letter = letters[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, new HashSet<>());
        positions.add(i);
        letterMap.putIfAbsent(letter, positions);
      }
      return letterMap;
    }

    @Override
    public String toString() {
      return String.format(STRING_FORMAT, text, correct, close);
    }

    /**
     * Returns the text of this instance.
     */
    public String getText() {
      return text;
    }

    /**
     * Returns the number of correct guesses.
     */
    public int getCorrect() {
      return correct;
    }

    /**
     * Returns the number of close guesses.
     */
    public int getClose() {
      return close;
    }

  }

}
