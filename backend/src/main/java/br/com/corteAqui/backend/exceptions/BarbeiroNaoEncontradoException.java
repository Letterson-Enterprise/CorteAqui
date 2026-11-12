package br.com.corteAqui.backend.exceptions;

public class BarbeiroNaoEncontradoException extends RuntimeException {

    public BarbeiroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}