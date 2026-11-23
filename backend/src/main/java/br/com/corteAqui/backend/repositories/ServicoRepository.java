package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servicos, Long> {
    boolean existsByNomeServico(String nomeServico);

    @Query("select s from Servicos s where s.id not in (?2) and s.nomeServico = ?1")
    Servicos verificarSeNomeExisteEmOutraLinha(String nomeServico, Long id);

    @Query("select s from Servicos s where s.barbeiro.usuario.id = ?1")
    List<Servicos> listaDeServicosBarbeiro(Long id);

    @Query("select s from Servicos s where s.id = ?1 and s.barbeiro.usuario.id = ?2")
    Optional <Servicos> encontrarServicoPorIdEBarbeiro(Long id, Long IdBarbeiro);

    @Modifying
    @Query("delete from Servicos s where s.id = ?1")
    void deletarServicoPorId(Long id);
}
