package kz.greetgo.diploma.controller.errors;

public class AuthError extends RestError {
  public AuthError(String message) {
    super(470, message);
  }
}
