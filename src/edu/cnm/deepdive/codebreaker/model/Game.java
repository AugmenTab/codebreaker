package edu.cnm.deepdive.codebreaker.model;

import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Manages information for the current running game. Creates a new game, restarts the game, checks
 * guesses for validity (and throws exceptions if they are not valid), and provides requested
 * information on the game.
 */
public class Game {

  private static final String BAD_GUESS_PATTERN_FORMAT = "^.*[^%s].*$";
  private static final String ILLEGAL_LENGTH_MESSAGE =
      "Invalid guess length: required=%d, provided=%d";
  private static final String ILLEGAL_CHARACTER_MESSAGE =
      "Guess includes invalid characters: required=%s; provided=%s.";

  private final Code code;
  private final List<Guess> guesses;
  private final String pool;
  private final int length;
  private final String badGuessPattern;

  /**
   * Starts a new game. Prompts {@link Code} to generate a new secret code using the provided
   * pool of legal characters, the desired number of characters in the code, and a source of
   * randomness to generate the code. Creates a new empty list of guesses.
   *
   * @param pool The pool of valid characters available for guesses and the {@link Code}.
   * @param length The number of characters in the secret {@link Code}.
   * @param rng The source of randomness.
   */
  public Game(String pool, int length, Random rng) {
    code = new Code(pool, length, rng);
    guesses = new LinkedList<>();
    this.pool = pool;
    this.length = length;
    badGuessPattern = String.format(BAD_GUESS_PATTERN_FORMAT, pool);
  }

  /**
   * Returns the secret {@link Code}.
   */
  public Code getCode() {
    return code;
  }

  /**
   * Returns the current list of guesses.
   */
  public List<Guess> getGuesses() {
    return Collections.unmodifiableList(guesses);
  }

  /**
   * Returns the pool of valid characters available for guesses and the {@link Code}.
   */
  public String getPool() {
    return pool;
  }

  /**
   * Returns the number of characters in the secret {@link Code}.
   */
  public int getLength() {
    return length;
  }

  /**
   * Returns the number of guesses made in the game so far.
   */
  public int getGuessCount() {
    return guesses.size();
  }

  /**
   * Prompts the validation and creation of a new {@link Guess} based on the {@code text} of the
   * user's guess. Checks the guess to ensure it will not throw a
   * {@link IllegalGuessCharacterException} or {@link IllegalGuessLengthException}, then gets the
   * guess from {@link Guess} and adds the guess to the {@code guesses} list.
   *
   * @param text The text of the user's guess.
   * @return The (confirmed valid) guess made by the user.
   * @throws IllegalGuessCharacterException Exception for using illegal characters.
   * @throws IllegalGuessLengthException Exception for having an invalid guess length.
   */
  public Guess guess(String text)
      throws IllegalGuessCharacterException, IllegalGuessLengthException {
    if (text.length() != length) {
      throw new IllegalGuessLengthException(
          String.format(ILLEGAL_LENGTH_MESSAGE, length, text.length()));
    }
    if (text.matches(badGuessPattern)) {
      throw new IllegalGuessCharacterException
          (String.format(ILLEGAL_CHARACTER_MESSAGE, pool, text));
    }
    Guess guess = code.new Guess(text);
    guesses.add(guess);
    return guess;
  }

  /**
   * Clears the guess list and restarts the game.
   */
  public void restart() {
    guesses.clear();
  }

}
