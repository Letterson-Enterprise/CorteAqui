package br.com.corteAqui.backend.dtos.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioRequestDTO(
        Long barbeiroId,
        LocalDate dataHorario,
        LocalTime hora
) {
}
