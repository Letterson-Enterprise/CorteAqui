package br.com.corteAqui.backend.exceptions;

public class HorarioNaoEncontradoException extends RuntimeException {

    public HorarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
