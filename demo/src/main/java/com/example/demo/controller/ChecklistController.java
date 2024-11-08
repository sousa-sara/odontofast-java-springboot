package com.example.demo.controller;

import com.example.demo.dto.ChecklistDTO;
import com.example.demo.service.interfaces.ChecklistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    @PostMapping
    public ResponseEntity<ChecklistDTO> criarChecklist(@Valid @RequestBody ChecklistDTO checklistDTO) {
        ChecklistDTO novoChecklist = checklistService.criarChecklist(checklistDTO);
        adicionarLinks(novoChecklist);
        return ResponseEntity.created(URI.create(novoChecklist.getRequiredLink("self").getHref())).body(novoChecklist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistDTO> obterChecklist(@PathVariable Long id) {
        ChecklistDTO checklist = checklistService.obterChecklistPorId(id);
        if (checklist == null) {
            return ResponseEntity.notFound().build();
        }
        adicionarLinks(checklist);
        return ResponseEntity.ok(checklist);
    }

    @GetMapping
    public ResponseEntity<List<ChecklistDTO>> listarChecklists() {
        List<ChecklistDTO> checklists = checklistService.listarChecklists();
        checklists.forEach(this::adicionarLinks);
        return ResponseEntity.ok(checklists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistDTO> atualizarChecklist(@PathVariable Long id, @Valid @RequestBody ChecklistDTO checklistDTO) {
        ChecklistDTO checklistAtualizado = checklistService.atualizarChecklist(id, checklistDTO);
        adicionarLinks(checklistAtualizado);
        return ResponseEntity.ok(checklistAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirChecklist(@PathVariable Long id) {
        if (checklistService.excluirChecklist(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(ChecklistDTO resource) {
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ChecklistController.class).obterChecklist(resource.getIdChecklist())).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ChecklistController.class).atualizarChecklist(resource.getIdChecklist(), resource)).withRel("atualizar"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ChecklistController.class).listarChecklists()).withRel("listarChecklists"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ChecklistController.class).excluirChecklist(resource.getIdChecklist())).withRel("excluir"));
    }
}
