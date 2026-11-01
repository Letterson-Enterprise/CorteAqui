package br.com.corteAqui.backend.components;

import br.com.corteAqui.backend.repositories.UsuarioRepository;
import br.com.corteAqui.backend.services.TokenJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    @Autowired
    private TokenJwtService tokenJwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        if(token != null) {
            String nomeUsuario = tokenJwtService.validarToken(token);
            UserDetails usuario = usuarioRepository.findByNomeUsuario(nomeUsuario).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
            Authentication autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request,response);
    }

    private String recuperarToken(HttpServletRequest requsicao) {
        String headerAutenticacao = requsicao.getHeader("Authorization");
        if(headerAutenticacao == null) return null;

        return headerAutenticacao.replace("Bearer ", "");
    }
}
