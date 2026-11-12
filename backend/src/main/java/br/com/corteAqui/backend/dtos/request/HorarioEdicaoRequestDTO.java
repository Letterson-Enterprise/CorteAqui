package br.com.corteAqui.backend.dtos.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioEdicaoRequestDTO(
        LocalDate dataHorario,
        LocalTime hora
) {
}