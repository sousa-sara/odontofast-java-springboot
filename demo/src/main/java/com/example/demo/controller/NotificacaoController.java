package com.example.demo.controller;
import com.example.demo.dto.NotificacaoDTO;
import com.example.demo.entity.Notificacao;
import com.example.demo.service.interfaces.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<NotificacaoDTO> criarNotificacao(@Valid @RequestBody NotificacaoDTO notificacaoDTO) {
        try {
            Notificacao novaNotificacao = notificacaoService.criarNotificacao(convertToEntity(notificacaoDTO));
            NotificacaoDTO resource = convertToDTO(novaNotificacao);
            adicionarLinks(resource);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            System.err.println("Erro ao criar notificação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> obterNotificacaoPorId(@PathVariable Long id) {
        Optional<Notificacao> notificacaoOptional = Optional.ofNullable(notificacaoService.obterNotificacaoPorId(id));
        return notificacaoOptional.map(notificacao -> {
            NotificacaoDTO resource = convertToDTO(notificacao);
            adicionarLinks(resource);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> listarNotificacoes() {
        List<Notificacao> notificacoes = notificacaoService.listarNotificacoes();
        List<NotificacaoDTO> notificacaoDTOs = notificacoes.stream()
                .map(this::convertToDTO)
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificacaoDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoDTO> atualizarNotificacao(@PathVariable Long id, @Valid @RequestBody NotificacaoDTO notificacaoDTO) {
        Notificacao notificacaoAtualizada = notificacaoService.atualizarNotificacao(id, convertToEntity(notificacaoDTO));
        NotificacaoDTO resource = convertToDTO(notificacaoAtualizada);
        adicionarLinks(resource);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNotificacao(@PathVariable Long id) {
        notificacaoService.excluirNotificacao(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(NotificacaoDTO notificacaoDTO) {
        notificacaoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).obterNotificacaoPorId(notificacaoDTO.getIdNotificacao())).withSelfRel());
        notificacaoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).atualizarNotificacao(notificacaoDTO.getIdNotificacao(), notificacaoDTO)).withRel("atualizar"));
        notificacaoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).listarNotificacoes()).withRel("listarNotificacoes"));
        notificacaoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacaoController.class).excluirNotificacao(notificacaoDTO.getIdNotificacao())).withRel("excluir"));
    }

    private Notificacao convertToEntity(NotificacaoDTO dto) {
        return new Notificacao(
                dto.getIdNotificacao(),
                dto.getMensagem(),
                dto.getTipoNotificacao(),
                dto.getDataEnvio(),
                dto.getLeitura()
        );
    }

    private NotificacaoDTO convertToDTO(Notificacao entity) {
        return new NotificacaoDTO(
                entity.getIdNotificacao(),
                entity.getMensagem(),
                entity.getTipoNotificacao(),
                entity.getDataEnvio(),
                entity.getLeitura()
        );
    }
}
