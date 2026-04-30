package com.kernelpanic.auth_service.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String id;
    private String token;
    private String nome;
    private String email;
    private String cargo;
}
