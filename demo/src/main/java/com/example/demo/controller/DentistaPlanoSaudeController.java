package com.example.demo.controller;
import com.example.demo.dto.DentistaPlanoSaudeDTO;
import com.example.demo.entity.DentistaPlanoSaude;
import com.example.demo.service.interfaces.DentistaPlanoSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dentistaplanosaude")
public class DentistaPlanoSaudeController {

    @Autowired
    private DentistaPlanoSaudeService dentistaPlanoSaudeService;

    @GetMapping
    public ResponseEntity<List<DentistaPlanoSaudeDTO>> listarAssociacoes() {
        List<DentistaPlanoSaudeDTO> recursos = dentistaPlanoSaudeService.listarAssociacoes().stream()
                .map(this::convertToDTOWithLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaPlanoSaudeDTO> obterAssociacaoPorId(@PathVariable Long id) {
        DentistaPlanoSaude associacao = dentistaPlanoSaudeService.obterAssociacaoPorId(id);
        if (associacao != null) {
            DentistaPlanoSaudeDTO resource = convertToDTOWithLinks(associacao);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/associar")
    public ResponseEntity<DentistaPlanoSaudeDTO> associarDentistaAoPlano(@RequestBody DentistaPlanoSaudeDTO dentistaPlanoSaudeDTO) {
        try {
            DentistaPlanoSaude associacao = dentistaPlanoSaudeService.associarDentistaAoPlano(dentistaPlanoSaudeDTO);
            DentistaPlanoSaudeDTO resource = convertToDTOWithLinks(associacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (Exception e) {
            System.err.println("Erro ao associar dentista ao plano: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaPlanoSaudeDTO> atualizarAssociacao(@PathVariable Long id, @RequestBody DentistaPlanoSaudeDTO dentistaPlanoSaudeDTO) {
        DentistaPlanoSaude associacaoAtualizada = dentistaPlanoSaudeService.atualizarAssociacao(id, dentistaPlanoSaudeDTO);
        if (associacaoAtualizada != null) {
            DentistaPlanoSaudeDTO resource = convertToDTOWithLinks(associacaoAtualizada);
            return ResponseEntity.ok(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAssociacao(@PathVariable Long id) {
        if (dentistaPlanoSaudeService.excluirAssociacao(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private DentistaPlanoSaudeDTO convertToDTO(DentistaPlanoSaude entity) {
        DentistaPlanoSaudeDTO dto = new DentistaPlanoSaudeDTO();
        dto.setDentistaId(entity.getDentista().getIdDentista());
        dto.setPlanoId(entity.getPlanoDeSaude().getIdPlano());
        return dto;
    }

    private DentistaPlanoSaudeDTO convertToDTOWithLinks(DentistaPlanoSaude associacao) {
        DentistaPlanoSaudeDTO resource = convertToDTO(associacao);
        Long id = associacao.getId();
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaPlanoSaudeController.class).obterAssociacaoPorId(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaPlanoSaudeController.class).listarAssociacoes()).withRel("listarAssociacoes"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaPlanoSaudeController.class).atualizarAssociacao(id, resource)).withRel("atualizar"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaPlanoSaudeController.class).excluirAssociacao(id)).withRel("excluir"));
        return resource;
    }
}
