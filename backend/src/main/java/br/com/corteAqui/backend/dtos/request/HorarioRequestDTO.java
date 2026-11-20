package br.com.corteAqui.backend.dtos.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioRequestDTO(
        @NotNull(message = "A data deve ser informada.")
        @FutureOrPresent(message = "A data não pode ser antirior a data atual.")
        LocalDate dataHorario,

        @NotNull(message = "A hora deve ser informada.")
        // @FutureOrPresent(message = "A hora não pode ser antirior a hora atual.")
        LocalTime hora
) {
}
