package com.example.demo.controller;
import com.example.demo.dto.AgendamentoDTO;
import com.example.demo.service.interfaces.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO novoAgendamento = agendamentoService.criarAgendamento(agendamentoDTO);
        adicionarLinks(novoAgendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> obterAgendamento(@PathVariable Long id) {
        AgendamentoDTO agendamento = agendamentoService.obterAgendamentoPorId(id);
        if (agendamento == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(agendamento);
        return ResponseEntity.ok(agendamento);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDTO>> listarAgendamentos() {
        // Chama o serviço passando um AgendamentoDTO vazio ou null
        List<AgendamentoDTO> agendamentos = agendamentoService.listarAgendamentos(null);

        // Adiciona links HATEOAS a cada DTO retornado
        List<AgendamentoDTO> agendamentoDTOs = agendamentos.stream()
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());

        return ResponseEntity.ok(agendamentoDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> atualizarAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoDTO agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, agendamentoDTO);
        if (agendamentoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(agendamentoAtualizado);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        if (agendamentoService.excluirAgendamento(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(AgendamentoDTO agendamentoDTO) {
        agendamentoDTO.add(linkTo(methodOn(AgendamentoController.class).obterAgendamento(agendamentoDTO.getIdAgendamento())).withSelfRel());
        agendamentoDTO.add(linkTo(methodOn(AgendamentoController.class).listarAgendamentos()).withRel("agendamentos"));
        agendamentoDTO.add(linkTo(methodOn(AgendamentoController.class).atualizarAgendamento(agendamentoDTO.getIdAgendamento(), agendamentoDTO)).withRel("atualizar"));
        agendamentoDTO.add(linkTo(methodOn(AgendamentoController.class).excluirAgendamento(agendamentoDTO.getIdAgendamento())).withRel("excluir"));
    }
}
