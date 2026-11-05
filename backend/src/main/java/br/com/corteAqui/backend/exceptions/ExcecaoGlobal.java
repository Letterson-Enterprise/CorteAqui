package br.com.corteAqui.backend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExcecaoGlobal {

    @ExceptionHandler(ExcecaoCamposInvalidos.class)
    public ResponseEntity<Map<String, String>> campoInvalido(ExcecaoCamposInvalidos excecao) {
        Map<String,String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(RecursoNaoEncontradoExcecao.class)
    public ResponseEntity<Map<String, String>> recursoNaoEncontrado(RecursoNaoEncontradoExcecao excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ExecaoInformacoesDuplicadas.class)
    public ResponseEntity<Map<String, String>> credenciaisDuplicadas(ExecaoInformacoesDuplicadas excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(ExcecaoListaVazia.class)
    public ResponseEntity<Map<String, String>> listaVazia(ExcecaoListaVazia excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ExececaoNumerosInvalidos.class)
    public ResponseEntity<Map<String, String>> numerosInvalidos(ExececaoNumerosInvalidos excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }
}
