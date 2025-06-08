package br.com.pucminas.moedaestudantil.exceptions.handlers;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
