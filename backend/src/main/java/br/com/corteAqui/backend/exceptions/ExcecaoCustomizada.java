package br.com.corteAqui.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExcecaoCustomizada extends RuntimeException {
    private String mensagem;
    private String tipo;
}
