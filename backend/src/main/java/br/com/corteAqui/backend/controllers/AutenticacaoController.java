package br.com.corteAqui.backend.controllers;

import br.com.corteAqui.backend.dtos.request.LoginRequestDTO;
import br.com.corteAqui.backend.dtos.response.TokenJwtResponseDTO;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.services.AutenticacaoService;
import br.com.corteAqui.backend.services.TokenJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenJwtService tokenJwtService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwtResponseDTO> login(@RequestBody LoginRequestDTO corpoLogin) {
        autenticacaoService.vericarCamposValidos(corpoLogin.nomeUsuario(), corpoLogin.senha());

        UsernamePasswordAuthenticationToken usuarioESenha = new UsernamePasswordAuthenticationToken(corpoLogin.nomeUsuario(), corpoLogin.senha());
        Authentication autenticacao = authenticationManager.authenticate(usuarioESenha);
        String token = tokenJwtService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenJwtResponseDTO(token));
    }
}
