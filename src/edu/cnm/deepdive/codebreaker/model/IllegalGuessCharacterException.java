package edu.cnm.deepdive.codebreaker.model;

/**
 * Exception thrown when the user enters a {@link edu.cnm.deepdive.codebreaker.model.Code.Guess}
 * with characters that are not in the pool of available characters defined in
 * {@link edu.cnm.deepdive.codebreaker.controller.Codebreaker}.
 */
public class IllegalGuessCharacterException extends IllegalArgumentException {

  public IllegalGuessCharacterException() {
  }

  public IllegalGuessCharacterException(String message) {
    super(message);
  }

  public IllegalGuessCharacterException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalGuessCharacterException(Throwable cause) {
    super(cause);
  }

}
