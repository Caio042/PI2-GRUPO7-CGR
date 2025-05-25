package br.com.projetointegradorgr3.estoqueglp.domain.service;

import br.com.projetointegradorgr3.estoqueglp.api.dto.TokenDto;
import br.com.projetointegradorgr3.estoqueglp.domain.exception.UsuarioExistenteException;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Usuario;
import br.com.projetointegradorgr3.estoqueglp.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Testes nas funcionalidades de login e cadastro no sistema")
class UsuarioServiceTest {

    private final UsuarioRepository repository = mock(UsuarioRepository.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final UsuarioService usuarioService = new UsuarioService(repository, new BCryptPasswordEncoder(), authenticationManager);
    private final String USUARIO = "email@email.com";

    @Test
    @DisplayName("Cenário 8: Login com credenciais incorretas")
    void login_deveDispararException_dadoUsuarioOuSenhaInvalidos() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Usuário ou senha invalidos"));

        assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> usuarioService.login(USUARIO, "mock"));
    }

    @Test
    @DisplayName("Cenário 6: Login com credenciais corretas")
    void login_deveRetornarTokenBasic_dadoLoginValido() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("", "", AuthorityUtils.createAuthorityList("ROLE_DEFAULT"));

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        assertThat(usuarioService.login(USUARIO, "mock"))
                .returns("ZW1haWxAZW1haWwuY29tOm1vY2s=", TokenDto::token);
    }

    @Test
    @DisplayName("Cenário 5: Cadastro de usuário com e-mail já existente")
    void cadastrar_deveDispararErro_dadoQueUsernameJaExiste() {
        Usuario usuarioExistente = new Usuario(USUARIO, "senha");
        when(repository.findByUsername(USUARIO)).thenReturn(Optional.of(usuarioExistente));

        Usuario novoUsuario = new Usuario(USUARIO, "outra senha");

        assertThatExceptionOfType(UsuarioExistenteException.class)
                .isThrownBy(() -> usuarioService.cadastrar(novoUsuario))
                .withMessage("Usuário \"email@email.com\" já em uso.");
    }

    @Test
    @DisplayName("Cenário 7: Cadastro permitido com username ainda não utilizado")
    void cadastrar_devePermitirCadastro_dadoQueUsuarioNaoExiste() {
        when(repository.findByUsername(USUARIO)).thenReturn(Optional.empty());

        Usuario novoUsuario = new Usuario(USUARIO, "mock");


        assertThatCode(() -> usuarioService.cadastrar(novoUsuario)).doesNotThrowAnyException();
    }
}
