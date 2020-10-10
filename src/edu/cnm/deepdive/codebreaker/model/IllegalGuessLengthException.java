package edu.cnm.deepdive.codebreaker.model;

/**
 * Exception thrown when the user enters a {@link edu.cnm.deepdive.codebreaker.model.Code.Guess}
 * with the incorrect length, as defined by
 * {@link edu.cnm.deepdive.codebreaker.controller.Codebreaker}.
 */
public class IllegalGuessLengthException extends IllegalArgumentException {

  public IllegalGuessLengthException() {
  }

  public IllegalGuessLengthException(String message) {
    super(message);
  }

  public IllegalGuessLengthException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalGuessLengthException(Throwable cause) {
    super(cause);
  }

}
