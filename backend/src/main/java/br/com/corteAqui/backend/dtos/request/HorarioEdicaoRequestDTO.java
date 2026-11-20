package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioEdicaoRequestDTO(
        @NotNull(message = "A data não pode ser nula.")
        @FutureOrPresent(message = "A data não pode ser antetior a atual.")
        LocalDate dataHorario,

        @NotNull(message = "A hora não pode ser nula.")
        // @FutureOrPresent(message = "A hora não pode ser anterior a atual.")
        LocalTime hora
) {
}