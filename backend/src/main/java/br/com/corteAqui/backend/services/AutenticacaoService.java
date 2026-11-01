package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.ExcecaoCamposInvalidos;
import br.com.corteAqui.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNomeUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }

    public void vericarCamposValidos(String nomeUsuario, String senha) {
        if(nomeUsuario.isBlank() || senha.isBlank()) {
            throw new ExcecaoCamposInvalidos("Todos os campos devem ser preenchidos", "ERROR");
        }
    }
}
