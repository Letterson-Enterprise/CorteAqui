package br.com.corteAqui.backend.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }

    @ExceptionHandler(ExececaoNumerosInvalidos.class)
    public ResponseEntity<Map<String, String>> numerosInvalidos(ExececaoNumerosInvalidos excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(ExcecaoDataInvalida.class)
    public ResponseEntity<Map<String, String>> dataInvalida(ExcecaoDataInvalida excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(ExcecaoHoraInvalida.class)
    public ResponseEntity<Map<String, String>> horaInvalida(ExcecaoHoraInvalida excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> errosValidacao(MethodArgumentNotValidException excecao) {
        Map<String, String> corpo = new HashMap<>();
        excecao.getBindingResult().getFieldErrors().forEach(erros -> {
            corpo.put(erros.getField(), erros.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(corpo);
    }

    @ExceptionHandler(ErroExcecao.class)
    public ResponseEntity<Map<String, String>> erroInternoServidor(ErroExcecao excecao) {
        Map<String, String> corpo = new HashMap<>();
        corpo.put(excecao.getMensagem(), excecao.getTipo());
        return ResponseEntity.internalServerError().body(corpo);
    }
}
