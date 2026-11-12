package br.com.corteAqui.backend.exceptions;

public class DataHoraInvalidaException extends RuntimeException {

    public DataHoraInvalidaException(String mensagem) {
        super(mensagem);
    }
}