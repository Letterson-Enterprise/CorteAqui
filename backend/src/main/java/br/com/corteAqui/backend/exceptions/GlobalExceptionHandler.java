package br.com.corteAqui.backend.exceptions;

import br.com.corteAqui.backend.dtos.response.ErroResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HorarioNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> tratarHorarioNaoEncontrado(
            HorarioNaoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(HorarioDuplicadoException.class)
    public ResponseEntity<ErroResponseDTO> tratarHorarioDuplicado(
            HorarioDuplicadoException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErroResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(DataHoraInvalidaException.class)
    public ResponseEntity<ErroResponseDTO> tratarDataHoraInvalida(
            DataHoraInvalidaException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(exception.getMessage()));
    }


    @ExceptionHandler(BarbeiroNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> tratarBarbeiroNaoEncontrado(
            BarbeiroNaoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErroResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(DadosInvalidosException.class)
    public ResponseEntity<ErroResponseDTO> tratarDadosInvalidos(
            DadosInvalidosException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponseDTO(exception.getMessage()));
    }
}
