package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Papel;
import br.com.corteAqui.backend.repositories.PapelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PapelService {
    @Autowired
    private PapelRepository papelRepository;

    public Papel encontrarPapel(Long id) {
        return papelRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Papel não encontrado", "ERROR"));
    }
}
