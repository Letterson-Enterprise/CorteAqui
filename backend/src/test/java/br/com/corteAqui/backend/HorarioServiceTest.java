package br.com.corteAqui.backend;

import br.com.corteAqui.backend.enums.StatusHorario;
import br.com.corteAqui.backend.models.Barbeiro;
import br.com.corteAqui.backend.models.Horario;
import br.com.corteAqui.backend.models.Papel;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.AgendamentoRepository;
import br.com.corteAqui.backend.repositories.BarbeiroRepository;
import br.com.corteAqui.backend.repositories.HorarioRepository;
import br.com.corteAqui.backend.repositories.PapelRepository;
import br.com.corteAqui.backend.repositories.UsuarioRepository;
import br.com.corteAqui.backend.services.HorarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class HorarioServiceTest {

    @Autowired
    private PapelRepository papelRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BarbeiroRepository barbeiroRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioService horarioService;

    @MockitoBean
    private AgendamentoRepository agendamentoRepository;

    private Barbeiro barbeiro;

    @BeforeEach
    void prepararBarbeiro() {
        Papel papel = new Papel();
        papel.setDescricaoPapel("BARBEIRO");
        papel = papelRepository.save(papel);

        Usuario usuario = new Usuario();
        usuario.setNomeUsuario("barbeiro_teste");
        usuario.setSenha("senha_teste");
        usuario.setPapel(papel);
        usuario = usuarioRepository.save(usuario);

        barbeiro = new Barbeiro();
        barbeiro.setNome("Joao");
        barbeiro.setSobreNome("Silva");
        barbeiro.setCpf("12345678901");
        barbeiro.setUsuario(usuario);
        barbeiro = barbeiroRepository.save(barbeiro);
    }

    private Horario criarHorario(LocalDate data, String hora) {
        Horario horario = new Horario();
        horario.setDataHorario(data);
        horario.setHora(Time.valueOf(hora));
        horario.setBarbeiro(barbeiro);

        return horarioService.cadastrar(horario);
    }

    @Test
    void deveCadastrarHorario() {
        Horario salvo = criarHorario(
                LocalDate.now().plusDays(1),
                "14:00:00"
        );

        assertNotNull(salvo.getId());
        assertEquals(barbeiro.getId(), salvo.getBarbeiro().getId());
        assertEquals(StatusHorario.DISPONIVEL, salvo.getStatusHorario());
    }

    @Test
    void deveListarHorariosDoBarbeiro() {
        criarHorario(LocalDate.now().plusDays(1), "14:00:00");
        criarHorario(LocalDate.now().plusDays(2), "15:00:00");

        List<Horario> horarios =
                horarioService.listarPorBarbeiro(barbeiro.getId());

        assertEquals(2, horarios.size());
    }

    @Test
    void deveEditarDataEHoraDoHorario() {
        Horario horario = criarHorario(
                LocalDate.now().plusDays(1),
                "14:00:00"
        );

        LocalDate novaData = LocalDate.now().plusDays(3);
        Time novaHora = Time.valueOf("16:00:00");

        String mensagem = horarioService.editar(
                horario.getId(),
                barbeiro.getId(),
                novaData,
                novaHora
        );

        Horario atualizado = horarioService.buscarPorId(
                horario.getId(),
                barbeiro.getId()
        );

        assertEquals("Horário alterado com sucesso.", mensagem);
        assertEquals(novaData, atualizado.getDataHorario());
        assertEquals(novaHora, atualizado.getHora());
    }

    @Test
    void deveExcluirHorario() {
        Horario horario = criarHorario(
                LocalDate.now().plusDays(1),
                "14:00:00"
        );

        horarioService.excluir(horario.getId(), barbeiro.getId());

        assertFalse(horarioRepository.existsById(horario.getId()));
    }

    @Test
    void naoDeveCadastrarHorarioDuplicado() {
        LocalDate data = LocalDate.now().plusDays(1);

        criarHorario(data, "14:00:00");

        Horario duplicado = new Horario();
        duplicado.setDataHorario(data);
        duplicado.setHora(Time.valueOf("14:00:00"));
        duplicado.setBarbeiro(barbeiro);

        assertThrows(
                ResponseStatusException.class,
                () -> horarioService.cadastrar(duplicado)
        );
    }

    @Test
    void naoDeveCadastrarHorarioComDataPassada() {
        Horario horario = new Horario();
        horario.setDataHorario(LocalDate.now().minusDays(1));
        horario.setHora(Time.valueOf("14:00:00"));
        horario.setBarbeiro(barbeiro);

        assertThrows(
                ResponseStatusException.class,
                () -> horarioService.cadastrar(horario)
        );
    }

    @Test
    void deveEditarHorarioComAgendamento() {
        Horario horario = criarHorario(
                LocalDate.now().plusDays(1),
                "14:00:00"
        );

        when(agendamentoRepository.existsByHorarioId(horario.getId()))
                .thenReturn(true);

        String mensagem = horarioService.editar(
                horario.getId(),
                barbeiro.getId(),
                LocalDate.now().plusDays(2),
                Time.valueOf("16:00:00")
        );

        assertEquals(
                "Horário alterado com sucesso. O cliente deverá ser notificado para decidir se deseja manter ou cancelar o agendamento.",
                mensagem
        );

        Horario atualizado = horarioService.buscarPorId(
                horario.getId(),
                barbeiro.getId()
        );

        assertEquals(LocalDate.now().plusDays(2), atualizado.getDataHorario());
        assertEquals(Time.valueOf("16:00:00"), atualizado.getHora());
    }

    @Test
    void deveRemoverHorarioComAgendamento() {
        Horario horario = criarHorario(
                LocalDate.now().plusDays(1),
                "14:00:00"
        );

        when(agendamentoRepository.existsByHorarioId(horario.getId()))
                .thenReturn(true);

        String mensagem = horarioService.excluir(
                horario.getId(),
                barbeiro.getId()
        );

        assertEquals(
                "Horário removido. O cliente deverá ser notificado para escolher outro horário.",
                mensagem
        );

        assertTrue(horarioRepository.existsById(horario.getId()));

        Horario removido = horarioService.buscarPorId(
                horario.getId(),
                barbeiro.getId()
        );

        assertEquals(StatusHorario.REMOVIDO, removido.getStatusHorario());
    }
}
