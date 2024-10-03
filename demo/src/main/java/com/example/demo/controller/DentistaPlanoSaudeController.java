package com.example.demo.controller;

import com.example.demo.dto.DentistaPlanoSaudeDTO;
import com.example.demo.entity.DentistaPlanoSaude;
import com.example.demo.service.DentistaPlanoSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dentistaplanosaude")
public class DentistaPlanoSaudeController {

    @Autowired
    private DentistaPlanoSaudeService dentistaPlanoSaudeService;

    // Endpoint para listar todas as associações
    @GetMapping
    public ResponseEntity<List<DentistaPlanoSaude>> listarAssociacoes() {
        List<DentistaPlanoSaude> associacoes = dentistaPlanoSaudeService.listarAssociacoes();
        return ResponseEntity.ok(associacoes);
    }

    // Endpoint para obter uma associação específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<DentistaPlanoSaude> obterAssociacaoPorId(@PathVariable Long id) {
        DentistaPlanoSaude associacao = dentistaPlanoSaudeService.obterAssociacaoPorId(id);
        return associacao != null ? ResponseEntity.ok(associacao) : ResponseEntity.notFound().build();
    }

    // Endpoint para criar uma nova associação entre dentista e plano de saúde
    @PostMapping("/associar")
    public ResponseEntity<DentistaPlanoSaude> associarDentistaAoPlano(@RequestBody DentistaPlanoSaudeDTO dentistaPlanoSaudeDTO) {
        DentistaPlanoSaude associacao = dentistaPlanoSaudeService.associarDentistaAoPlano(dentistaPlanoSaudeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(associacao);
    }

    // Endpoint para atualizar uma associação existente
    @PutMapping("/{id}")
    public ResponseEntity<DentistaPlanoSaude> atualizarAssociacao(@PathVariable Long id, @RequestBody DentistaPlanoSaudeDTO dentistaPlanoSaudeDTO) {
        DentistaPlanoSaude associacaoAtualizada = dentistaPlanoSaudeService.atualizarAssociacao(id, dentistaPlanoSaudeDTO);
        return associacaoAtualizada != null ? ResponseEntity.ok(associacaoAtualizada) : ResponseEntity.notFound().build();
    }

    // Endpoint para excluir uma associação entre dentista e plano de saúde
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAssociacao(@PathVariable Long id) {
        boolean excluido = dentistaPlanoSaudeService.excluirAssociacao(id);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
