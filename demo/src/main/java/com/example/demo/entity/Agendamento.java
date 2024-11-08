package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "c_op_agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendamento_seq")
    @SequenceGenerator(name = "agendamento_seq", sequenceName = "seq_agendamento", allocationSize = 1)
    private Long idAgendamento;

    @NotNull(message = "A data agendada é obrigatória.")
    @Temporal(TemporalType.DATE)
    private Date dataAgendada;

    @NotNull(message = "O horário agendado é obrigatório.")
    private String horarioAgendado;

    private String statusTratamento;

    private String descricaoAgendamento;

    @ManyToOne
    @JoinColumn(name = "dentista_id_dentista", nullable = false)
    private Dentista dentista;

    @ManyToOne
    @JoinColumn(name = "usuario_id_usuario", nullable = false)
    private Usuario usuario;

    // Construtor adicional
    public Agendamento(Long idAgendamento, Date dataAgendada, String horarioAgendado, String statusTratamento, String descricaoAgendamento, Long dentistaId, Long usuarioId) {
        this.idAgendamento = idAgendamento;
        this.dataAgendada = dataAgendada;
        this.horarioAgendado = horarioAgendado;
        this.statusTratamento = statusTratamento;
        this.descricaoAgendamento = descricaoAgendamento;
        this.dentista = new Dentista(dentistaId); // Supondo que você tenha um construtor no Dentista que aceite um ID
        this.usuario = new Usuario(usuarioId);     // Supondo que você tenha um construtor no Usuario que aceite um ID
    }

    // Métodos para obter os IDs do dentista e do usuário
    public Long getDentistaId() {
        return dentista != null ? dentista.getIdDentista() : null; // Supondo que getIdDentista() exista em Dentista
    }

    public Long getUsuarioId() {
        return usuario != null ? usuario.getIdUsuario() : null; // Supondo que getIdUsuario() exista em Usuario
    }

    // Métodos para definir os IDs do dentista e do usuário
    public void setDentistaId(Long dentistaId) {
        this.dentista = new Dentista(dentistaId); // Supondo que você tenha um construtor no Dentista que aceite um ID
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuario = new Usuario(usuarioId); // Supondo que você tenha um construtor no Usuario que aceite um ID
    }
}
