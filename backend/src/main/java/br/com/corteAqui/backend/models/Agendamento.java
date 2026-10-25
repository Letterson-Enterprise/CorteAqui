package br.com.corteAqui.backend.models;

import br.com.corteAqui.backend.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_agendamentos")
@Data
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idHorario", nullable = false)
    private Horario horario;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgendamento;

    @ManyToOne
    @JoinColumn(name = "idServico", nullable = false)
    private Servicos servicos;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

}
