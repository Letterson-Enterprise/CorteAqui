package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servicos, Long> {
    boolean existsByNomeServico(String nomeServico);

    @Query("select s from Servicos s where s.id not in (?2) and s.nomeServico = ?1")
    Servicos verficarSeNomeExisteEmOutraLinha(String nomeServico, Long id);

    @Query("select s from Servicos s join Barbeiro r on s.barbeiro.id = r.id where s.barbeiro.id = ?1")
    List<Servicos> listaDeServicosBarbeiro(Long id);

    @Query("select s from Servicos s join Barbeiro r on s.barbeiro.id = r.id where s.id = ?1 and r.id = ?2")
    Optional <Servicos> encontrarServicoPorIdEBarbeiro(Long id, Long IdBarbeiro);
}
