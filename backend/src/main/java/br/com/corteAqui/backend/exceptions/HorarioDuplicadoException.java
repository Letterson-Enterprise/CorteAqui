package br.com.corteAqui.backend.exceptions;

public class HorarioDuplicadoException extends RuntimeException {

    public HorarioDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
