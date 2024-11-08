package com.example.demo.controller;
import com.example.demo.dto.TratamentoDTO;
import com.example.demo.service.interfaces.TratamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tratamentos")
public class TratamentoController {

    @Autowired
    private TratamentoService tratamentoService;

    @PostMapping
    public ResponseEntity<TratamentoDTO> criarTratamento(@Valid @RequestBody TratamentoDTO tratamentoDTO) {
        TratamentoDTO tratamentoSalvo = tratamentoService.criarTratamento(tratamentoDTO);
        adicionarLinks(tratamentoSalvo);

        return ResponseEntity.created(URI.create(tratamentoSalvo.getRequiredLink("self").getHref())).body(tratamentoSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamentoDTO> obterTratamento(@PathVariable Long id) {
        TratamentoDTO tratamento = tratamentoService.obterTratamentoPorId(id);
        adicionarLinks(tratamento);
        return ResponseEntity.ok(tratamento);
    }

    @GetMapping
    public ResponseEntity<List<TratamentoDTO>> listarTratamentos() {
        List<TratamentoDTO> tratamentos = tratamentoService.listarTratamentos();
        tratamentos.forEach(this::adicionarLinks);
        return ResponseEntity.ok(tratamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamentoDTO> atualizarTratamento(@PathVariable Long id, @Valid @RequestBody TratamentoDTO tratamentoDTO) {
        TratamentoDTO tratamentoAtualizado = tratamentoService.atualizarTratamento(id, tratamentoDTO);
        adicionarLinks(tratamentoAtualizado);

        return ResponseEntity.ok(tratamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTratamento(@PathVariable Long id) {
        tratamentoService.excluirTratamento(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(TratamentoDTO tratamentoDTO) {
        tratamentoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TratamentoController.class).obterTratamento(tratamentoDTO.getId_tratamento())).withSelfRel());
        tratamentoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TratamentoController.class).atualizarTratamento(tratamentoDTO.getId_tratamento(), tratamentoDTO)).withRel("atualizar"));
        tratamentoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TratamentoController.class).excluirTratamento(tratamentoDTO.getId_tratamento())).withRel("excluir"));
        tratamentoDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TratamentoController.class).listarTratamentos()).withRel("listarTratamentos"));
    }
}
