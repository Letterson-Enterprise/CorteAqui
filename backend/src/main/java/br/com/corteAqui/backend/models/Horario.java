package br.com.corteAqui.backend.models;

import br.com.corteAqui.backend.enums.StatusHorario;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_horarios")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Time hora;

    @Column(nullable = false)
    private LocalDate dataHorario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusHorario statusHorario;

    @ManyToOne
    @JoinColumn(name = "idBarbeiro", nullable = false)
    @JsonIgnore
    private Barbeiro barbeiro;

}
