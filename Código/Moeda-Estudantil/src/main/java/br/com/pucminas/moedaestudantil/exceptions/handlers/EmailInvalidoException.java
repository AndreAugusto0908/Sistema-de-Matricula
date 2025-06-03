package br.com.pucminas.moedaestudantil.exceptions.handlers;

public class EmailInvalidoException extends RuntimeException {
  public EmailInvalidoException(String message) {
    super(message);
  }
}
