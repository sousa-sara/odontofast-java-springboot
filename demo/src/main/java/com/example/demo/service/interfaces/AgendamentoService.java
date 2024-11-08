package com.example.demo.service.interfaces;
import com.example.demo.dto.AgendamentoDTO;
import java.util.List;

public interface AgendamentoService {

    AgendamentoDTO criarAgendamento(AgendamentoDTO agendamentoDTO);
    AgendamentoDTO obterAgendamentoPorId(Long id);
    List<AgendamentoDTO> listarAgendamentos(AgendamentoDTO agendamentoDTO);
    AgendamentoDTO atualizarAgendamento(Long id, AgendamentoDTO agendamentoDTO);
    boolean excluirAgendamento(Long id);

}
