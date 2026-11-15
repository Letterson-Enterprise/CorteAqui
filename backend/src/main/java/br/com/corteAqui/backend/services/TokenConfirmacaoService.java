package br.com.corteAqui.backend.services;

import br.com.corteAqui.backend.exceptions.ErroExcecao;
import br.com.corteAqui.backend.exceptions.RecursoNaoEncontradoExcecao;
import br.com.corteAqui.backend.models.Cliente;
import br.com.corteAqui.backend.models.Token;
import br.com.corteAqui.backend.models.Usuario;
import br.com.corteAqui.backend.repositories.ClienteRepository;
import br.com.corteAqui.backend.repositories.TokenRepository;
import br.com.corteAqui.backend.repositories.UsuarioRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenConfirmacaoService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Metodo usado para gerar uma string aleatoria de 6 digitos.
     * É usado o metodo nextAlphanumeric da classe RandomStringUtils
     * da biblioteca Apache.commons.lang3.
     * Exemplo: fjipOW
     * @Autor: Jefferson Matheus
     * @return String
     */
    public String gerarTokenConfirmacao() {
        return RandomStringUtils.secure().nextAlphanumeric(6);
    }


    /**
     * Metodo utilizado para persistir o token de confirmação
     * gerado para o base de dados.
     * @Autor: Jefferson Matheus
     * @Params: usuarioId, id do usuario que será usado gravar na base de dados.
     * @Return: void
     */
    public void persistirTokenBanco(Long usuarioId) throws ErroExcecao {
        String tokenConfimacao = gerarTokenConfirmacao();

        try {
            Cliente clienteBuscado = clienteRepository.encontrarClientePeloUsuario(usuarioId).orElseThrow(() -> new RecursoNaoEncontradoExcecao("Usuario não encontrado.", "Recurso não encontrado"));
            Token token = new Token();
            token.setToken(tokenConfimacao);
            token.setCliente(clienteBuscado);
            tokenRepository.save(token);
        } catch (Exception e) {
            throw new ErroExcecao("Erro interno do servidor ao persistir o token: " + e.getMessage(), "Erro interno do servidor.");
        }
    }
}
