package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Papel;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PapelService papelService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario cadastrarUsuario(String nomeUsuario, String senha, String email, Long idPapel) {
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNomeUsuario(nomeUsuario);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        Papel papelUsuario = papelService.encontrarPapel(idPapel);
        novoUsuario.setPapel(papelUsuario);
        boolean usuarioExiste = usuarioRepository.existsByNomeUsuario(novoUsuario.getNomeUsuario());
        boolean emailExiste = emailService.verificarEmailExistente(email);

        if (usuarioExiste && emailExiste) {
            throw new ExecaoInformacoesDuplicadas("Já existe um usuário com nome de usuario, email ou senha iguais", "ERROR");
        }

        novoUsuario = usuarioRepository.save(novoUsuario);

        emailService.cadastrarEmail(email, novoUsuario);

        return novoUsuario;
    }

    @Transactional
    public void atualizarUsuario(Long id, String nomeUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao(
                        "Usuário não encontrado no sistema.", "ERROR"));

        if (usuarioRepository.existsByNomeUsuario(nomeUsuario)
                && !usuarioExistente.getNomeUsuario().equals(nomeUsuario)) {
            throw new ExecaoInformacoesDuplicadas(
                    "Já existe um usuário com esse nome de usuário.", "ERROR");
        }

        usuarioExistente.setNomeUsuario(nomeUsuario);
        usuarioRepository.save(usuarioExistente);
    }
}
