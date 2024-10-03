package com.example.demo.controller;

import com.example.demo.dto.NotificacaoDTO;
import com.example.demo.entity.Notificacao;
import com.example.demo.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    // Endpoint para listar todas as notificações
    @GetMapping
    public ResponseEntity<List<Notificacao>> listarNotificacoes() {
        List<Notificacao> notificacoes = notificacaoService.listarNotificacoes();
        return ResponseEntity.ok(notificacoes);
    }

    // Endpoint para obter uma notificação específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> obterNotificacaoPorId(@PathVariable Long id) {
        Notificacao notificacao = notificacaoService.obterNotificacaoPorId(id);
        return notificacao != null ? ResponseEntity.ok(notificacao) : ResponseEntity.notFound().build();
    }

    // Endpoint para criar uma nova notificação
    @PostMapping
    public ResponseEntity<Notificacao> criarNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = new Notificacao();
        // Convertendo DTO para entidade
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setTipoNotificacao(notificacaoDTO.getTipoNotificacao());
        notificacao.setDataEnvio(notificacaoDTO.getDataEnvio());
        notificacao.setLeitura(notificacaoDTO.getLeitura());
        Notificacao novaNotificacao = notificacaoService.criarNotificacao(notificacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNotificacao);
    }

    // Endpoint para atualizar uma notificação existente
    @PutMapping("/{id}")
    public ResponseEntity<Notificacao> atualizarNotificacao(@PathVariable Long id, @RequestBody NotificacaoDTO notificacaoDTO) {
        Notificacao notificacao = new Notificacao();
        notificacao.setIdNotificacao(id);
        notificacao.setMensagem(notificacaoDTO.getMensagem());
        notificacao.setTipoNotificacao(notificacaoDTO.getTipoNotificacao());
        notificacao.setDataEnvio(notificacaoDTO.getDataEnvio());
        notificacao.setLeitura(notificacaoDTO.getLeitura());

        Notificacao notificacaoAtualizada = notificacaoService.atualizarNotificacao(id, notificacao);
        return notificacaoAtualizada != null ? ResponseEntity.ok(notificacaoAtualizada) : ResponseEntity.notFound().build();
    }

    // Endpoint para excluir uma notificação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNotificacao(@PathVariable Long id) {
        notificacaoService.excluirNotificacao(id);
        return ResponseEntity.noContent().build();
    }

}
