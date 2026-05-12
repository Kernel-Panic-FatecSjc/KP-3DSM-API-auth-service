package com.kernelpanic.auth_service.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kernelpanic.auth_service.dtos.LoginRequestDTO;
import com.kernelpanic.auth_service.dtos.LoginResponseDTO;
import com.kernelpanic.auth_service.entidades.Usuario;
import com.kernelpanic.auth_service.repositorios.UsuarioAuthRepositorio;
import com.kernelpanic.auth_service.excecoes.personalizado.UsuarioNaoEncontradoException;

@Service
public class AuthService {

    @Autowired
    private UsuarioAuthRepositorio repositorio;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;

    public LoginResponseDTO loginViaDTO(LoginRequestDTO dto) {
        Usuario usuario = repositorio.findByEmail(dto.getEmail())
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Nenhum usuário encontrado com este email"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario);
        return converterParaLoginResponseDTO(usuario, token);
    }

    public String login(String email, String senha) {
        Usuario usuario = repositorio.findByEmail(email)
            .orElseThrow(() -> new UsuarioNaoEncontradoException("Nenhum usuário encontrado com este email"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return jwtService.gerarToken(usuario);
    }

    private LoginResponseDTO converterParaLoginResponseDTO(Usuario usuario, String token) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(usuario.getId().toString());
        dto.setToken(token);
        dto.setEmail(usuario.getEmail());
        dto.setCargo(usuario.getCargo());
        dto.setNome(usuario.getNome());
        return dto;
    }
}
