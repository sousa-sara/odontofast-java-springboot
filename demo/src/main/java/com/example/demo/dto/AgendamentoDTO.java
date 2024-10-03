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
public class AgendamentoDTO {

    private Long idAgendamento;
    private Date dataAgendada;
    private String horarioAgendado;
    private String statusTratamento;
    private String descricaoAgendamento;
    private Long dentistaId;
    private Long usuarioId;

}
