package br.com.edu.uninter.monitoria.service;

import br.com.edu.uninter.monitoria.config.TokenProvider;
import br.com.edu.uninter.monitoria.dto.LoginRequest;
import br.com.edu.uninter.monitoria.dto.LoginResponse;
import br.com.edu.uninter.monitoria.dto.RegisterRequest;
import br.com.edu.uninter.monitoria.exception.UsuarioExistsException;
import br.com.edu.uninter.monitoria.model.Usuario;
import br.com.edu.uninter.monitoria.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse login(
            LoginRequest request,
            AuthenticationManager authenticationManager
    ) {

        var usernamePassword =
                new UsernamePasswordAuthenticationToken(
                        request.nome(),
                        request.senha()
                );

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenProvider.generateToken(
                (Usuario) auth.getPrincipal()
        );

        return new LoginResponse(token);
    }


    @Transactional
    public void register(RegisterRequest request) {

        if (usuarioRepository.findByNome(request.nome()).isPresent()) {
            throw new UsuarioExistsException();
        }

        Usuario usuario = new Usuario(
                request.nome(),
                passwordEncoder.encode(request.senha()),
                request.materia()
        );

        usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepository.findByNome(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado")
                );
    }
}