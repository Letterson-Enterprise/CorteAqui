package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.ExcecaoCamposInvalidos;
import br.com.corteAqui.backend.exceptions.ExecaoInformacoesDuplicadas;
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
        if (nomeUsuario.isBlank() || senha.isBlank() || email.isBlank()) {
            throw new ExcecaoCamposInvalidos("Todos os campos devem ser preenchidos", "ERROR");
        }

        novoUsuario.setNomeUsuario(nomeUsuario);
        novoUsuario.setSenha(passwordEncoder.encode(senha));
        Papel papelUsuario = papelService.encontrarPapel(idPapel);
        novoUsuario.setPapel(papelUsuario);
        boolean usuarioExiste = verificarUsuarioExistente(novoUsuario);

        if (usuarioExiste) {
            throw new ExecaoInformacoesDuplicadas("Já existe um usuário com nome de usuario, email ou senha iguais", "ERROR");
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
            ;
        }

        return usuarioExiste;
    }
}
