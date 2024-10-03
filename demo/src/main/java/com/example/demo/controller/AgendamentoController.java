package com.example.demo.controller;

import com.example.demo.dto.AgendamentoDTO;
import com.example.demo.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoDTO);
        return ResponseEntity.ok(novoAgendamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> obterAgendamento(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.obterAgendamentoPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentos() {
        List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizarAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, agendamentoDTO);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        agendamentoService.excluirAgendamento(id);
        return ResponseEntity.noContent().build();
    }
}
