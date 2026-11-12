package br.com.corteAqui.backend.dtos.response;

import br.com.corteAqui.backend.enums.StatusHorario;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioResponseDTO(
        Long id,
        LocalDate dataHorario,
        LocalTime hora,
        StatusHorario statusHorario,
        Long barbeiroId
) {
}