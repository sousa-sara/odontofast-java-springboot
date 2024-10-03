package com.example.demo.controller;

import com.example.demo.dto.TratamentoDTO;
import com.example.demo.service.TratamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tratamentos")
public class TratamentoController {

    @Autowired
    private TratamentoService tratamentoService;

    @PostMapping
    public ResponseEntity<TratamentoDTO> criarTratamento(@Valid @RequestBody TratamentoDTO tratamentoDTO) {
        TratamentoDTO novoTratamento = tratamentoService.criarTratamento(tratamentoDTO);
        return ResponseEntity.ok(novoTratamento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamentoDTO> obterTratamento(@PathVariable Long id) {
        TratamentoDTO tratamento = tratamentoService.obterTratamentoPorId(id);
        return ResponseEntity.ok(tratamento);
    }

    @GetMapping
    public ResponseEntity<List<TratamentoDTO>> listarTratamentos() {
        List<TratamentoDTO> tratamentos = tratamentoService.listarTratamentos();
        return ResponseEntity.ok(tratamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamentoDTO> atualizarTratamento(@PathVariable Long id, @Valid @RequestBody TratamentoDTO tratamentoDTO) {
        TratamentoDTO tratamentoAtualizado = tratamentoService.atualizarTratamento(id, tratamentoDTO);
        return ResponseEntity.ok(tratamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTratamento(@PathVariable Long id) {
        tratamentoService.excluirTratamento(id);
        return ResponseEntity.noContent().build();
    }
}
