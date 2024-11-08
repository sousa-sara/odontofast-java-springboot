package com.example.demo.controller;
import com.example.demo.dto.DentistaDTO;
import com.example.demo.entity.Dentista;
import com.example.demo.service.interfaces.DentistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dentistas")
public class DentistaController {

    @Autowired
    private DentistaService dentistaService;

    @PostMapping
    public ResponseEntity<DentistaDTO> criarDentista(@Valid @RequestBody DentistaDTO dentistaDTO) {
        try {
            Dentista novoDentista = dentistaService.criarDentista(new Dentista(dentistaDTO));
            DentistaDTO dentista = new DentistaDTO(novoDentista);
            adicionarLinks(dentista);
            return ResponseEntity.status(HttpStatus.CREATED).body(dentista);
        } catch (Exception e) {
            System.err.println("Erro ao criar dentista: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaDTO> obterDentista(@PathVariable Long id) {
        Optional<Dentista> dentistaOptional = dentistaService.obterDentistaPorId(id);
        return dentistaOptional.map(dentista -> {
            DentistaDTO resource = new DentistaDTO(dentista);
            adicionarLinks(resource);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DentistaDTO>> listarDentistas() {
        List<Dentista> dentistas = dentistaService.listarDentistas();
        return ResponseEntity.ok(
                dentistas.stream()
                        .map(DentistaDTO::new)
                        .peek(this::adicionarLinks)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaDTO> atualizarDentista(@PathVariable Long id, @Valid @RequestBody DentistaDTO dentistaDTO) {
        Dentista dentistaAtualizado = dentistaService.atualizarDentista(id, new Dentista(dentistaDTO));
        DentistaDTO resource = new DentistaDTO(dentistaAtualizado);
        adicionarLinks(resource);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDentista(@PathVariable Long id) {
        dentistaService.excluirDentista(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(DentistaDTO dentistaDTO) {
        dentistaDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaController.class).obterDentista(dentistaDTO.getIdDentista())).withSelfRel());
        dentistaDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaController.class).atualizarDentista(dentistaDTO.getIdDentista(), dentistaDTO)).withRel("atualizar"));
        dentistaDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaController.class).listarDentistas()).withRel("listarDentistas"));
        dentistaDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DentistaController.class).excluirDentista(dentistaDTO.getIdDentista())).withRel("excluir"));
    }
}
