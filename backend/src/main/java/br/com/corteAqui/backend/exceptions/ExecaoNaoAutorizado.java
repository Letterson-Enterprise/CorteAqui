package br.com.corteAqui.backend.exceptions;

public class ExecaoNaoAutorizado extends ExcecaoCustomizada {
    public ExecaoNaoAutorizado(String mensagem, String tipo) {
        super(mensagem, tipo);
    }
}
