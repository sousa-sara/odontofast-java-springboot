package com.example.demo.controller;

import com.example.demo.dto.ChecklistDTO;
import com.example.demo.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    @PostMapping
    public ResponseEntity<ChecklistDTO> criarChecklist(@Valid @RequestBody ChecklistDTO checklistDTO) {
        ChecklistDTO novoChecklist = checklistService.criarChecklist(checklistDTO);
        return ResponseEntity.ok(novoChecklist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistDTO> obterChecklist(@PathVariable Long id) {
        ChecklistDTO checklist = checklistService.obterChecklistPorId(id);
        return ResponseEntity.ok(checklist);
    }

    @GetMapping
    public ResponseEntity<List<ChecklistDTO>> listarChecklists() {
        List<ChecklistDTO> checklists = checklistService.listarChecklists();
        return ResponseEntity.ok(checklists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistDTO> atualizarChecklist(@PathVariable Long id, @Valid @RequestBody ChecklistDTO checklistDTO) {
        ChecklistDTO checklistAtualizado = checklistService.atualizarChecklist(id, checklistDTO);
        return ResponseEntity.ok(checklistAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirChecklist(@PathVariable Long id) {
        checklistService.excluirChecklist(id);
        return ResponseEntity.noContent().build();
    }
}
