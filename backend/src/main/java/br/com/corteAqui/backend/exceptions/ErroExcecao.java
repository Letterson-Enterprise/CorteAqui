package br.com.corteAqui.backend.exceptions;

public class ErroExcecao extends ExcecaoCustomizada {
    public ErroExcecao(String mensagem, String tipo) {
        super(mensagem, tipo);
    }
}

