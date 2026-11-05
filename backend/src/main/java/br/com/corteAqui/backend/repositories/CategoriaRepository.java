package br.com.corteAqui.backend.repositories;

import br.com.corteAqui.backend.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNome(String nome);

    @Query("select c from Categoria c where c.id not in (?2) and c.nome = ?1")
    List<Categoria> verificarSeNomeExisteEmOutraLinha(String nome, Long id);
}
