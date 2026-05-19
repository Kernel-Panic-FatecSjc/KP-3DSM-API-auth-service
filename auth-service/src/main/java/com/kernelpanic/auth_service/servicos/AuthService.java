package com.kernelpanic.auth_service.servicos;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kernelpanic.auth_service.dtos.LoginRequestDTO;
import com.kernelpanic.auth_service.dtos.LoginResponseDTO;
import com.kernelpanic.auth_service.entidades.Usuario;
import com.kernelpanic.auth_service.excecoes.personalizado.UsuarioNaoEncontradoException;
import com.kernelpanic.auth_service.repositorios.UsuarioAuthRepositorio;

@Service
public class AuthService {

    @Autowired
    private UsuarioAuthRepositorio repositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final RestTemplate restTemplate = new RestTemplate();

    public void cadastrar(Usuario usuario) {
        repositorio.save(usuario);
    }

    public LoginResponseDTO loginViaDTO(LoginRequestDTO dto) {
        Usuario usuario = repositorio.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Nenhum usuário encontrado com este email"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario);
        String idReal = buscarIdNoUsuarioService(dto.getEmail());
        return converterParaLoginResponseDTO(usuario, token, idReal);
    }

    public String login(String email, String senha) {
        Usuario usuario = repositorio.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Nenhum usuário encontrado com este email"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return jwtService.gerarToken(usuario);
    }

    private String buscarIdNoUsuarioService(String email) {
        try {
            List<Map> usuarios = restTemplate.getForObject("http://usuario-service-app:8083/usuario/todos", List.class);
            if (usuarios != null) {
                for (Map u : usuarios) {
                    if (email.equals(u.get("email"))) {
                        return String.valueOf(u.get("id"));
                    }
                }
            }
        } catch (Exception e) {
            // fallback: retorna id do auth
        }
        return null;
    }

    private LoginResponseDTO converterParaLoginResponseDTO(Usuario usuario, String token, String idReal) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(idReal != null ? idReal : usuario.getId().toString());
        dto.setToken(token);
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setNome(usuario.getNome());
        return dto;
    }
}
