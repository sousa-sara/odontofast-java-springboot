package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

    private Long idNotificacao;
    private String mensagem;
    private String tipoNotificacao;
    private Date dataEnvio;
    private Character leitura;

}
