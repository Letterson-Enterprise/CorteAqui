package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.ExcecaoCamposInvalidos;
import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Papel;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        novoUsuario.setPapel(papelService.encontrarPapel(idPapel));

        boolean usuarioExiste = verificarUsuarioExistente(novoUsuario);
        if (usuarioExiste) {
            throw new ExecaoInformacoesDuplicadas("Já existe um usuário com nome de usuário, email ou senha iguais", "ERROR");
        }

        novoUsuario = usuarioRepository.save(novoUsuario);

        emailService.cadastrarEmail(email, novoUsuario);

        return novoUsuario;
    }

    public boolean verificarUsuarioExistente(Usuario usuario) {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();
        boolean usuarioExiste = false;
        for (Usuario usuarioNaLista : todosUsuarios) {
            if (usuario.getNomeUsuario().equals(usuarioNaLista.getNomeUsuario()) ||
                    usuario.getEmails().equals(usuarioNaLista.getEmails()) ||
                    usuario.getSenha().equals(usuarioNaLista.getSenha())) {
                usuarioExiste = true;
                break;
            }
        }

        return usuarioExiste;
    }

    @Transactional
    public void atualizarUsuario(Long id, String nomeUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoExcecao("Usuário não encontrado no sistema.", "ERROR"));

        usuarioRepository.save(usuarioExistente);
    }
}
