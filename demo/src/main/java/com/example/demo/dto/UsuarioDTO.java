package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;
    private String nomeUsuario;
    private String senhaUsuario;
    private String emailUsuario;
    private String nrCarteira;
    private Long dentistaIdDentista;
    private Long planoIdPlano;
    private Long notIdNotificacao;

}