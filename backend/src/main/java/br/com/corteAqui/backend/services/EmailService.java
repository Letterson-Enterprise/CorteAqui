package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.models.Email;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;

    public void cadastrarEmail(String email, Usuario usuario) {
        Email novoEmail = new Email();

        novoEmail.setEmail(email);
        novoEmail.setStatusAtivo(true);
        novoEmail.setUsuario(usuario);

        emailRepository.save(novoEmail);
    }

    public boolean verificarEmailExistente(String email) {
        return emailRepository.existsByEmail(email);
    }
}
