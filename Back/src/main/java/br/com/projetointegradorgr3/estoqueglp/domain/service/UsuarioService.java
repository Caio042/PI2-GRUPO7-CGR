package br.com.projetointegradorgr3.estoqueglp.domain.service;

import br.com.projetointegradorgr3.estoqueglp.api.dto.TokenDto;
import br.com.projetointegradorgr3.estoqueglp.domain.exception.UsuarioExistenteException;
import br.com.projetointegradorgr3.estoqueglp.domain.model.Usuario;
import br.com.projetointegradorgr3.estoqueglp.domain.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário '" + username + "' não existe"));
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        if (repository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new UsuarioExistenteException(usuario.getUsername());
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRole("ROLE_DEFAULT");

        return repository.save(usuario);
    }

    public String usuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public TokenDto login(String usuario, String senha) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, senha));
        boolean isAdmin = authenticate.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ROLE_ADMIN"::equals);
        String token = Base64.getEncoder().encodeToString((usuario + ":" + senha).getBytes());

        return new TokenDto(token, isAdmin);
    }
}
