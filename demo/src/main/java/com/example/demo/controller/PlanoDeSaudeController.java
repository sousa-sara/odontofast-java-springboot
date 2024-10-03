package com.example.demo.controller;

import com.example.demo.dto.PlanoDeSaudeDTO;
import com.example.demo.entity.PlanoDeSaude;
import com.example.demo.service.PlanoDeSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planos")
public class PlanoDeSaudeController {

    @Autowired
    private PlanoDeSaudeService planoDeSaudeService;

    @PostMapping
    public ResponseEntity<PlanoDeSaudeDTO> criarPlanoSaude(@Valid @RequestBody PlanoDeSaudeDTO planoDeSaudeDTO) {
        PlanoDeSaude novoPlanoDeSaude = planoDeSaudeService.criarPlanoDeSaude(convertToEntity(planoDeSaudeDTO));
        return ResponseEntity.ok(convertToDTO(novoPlanoDeSaude));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDeSaudeDTO> obterPlanoDeSaude(@PathVariable Long id) {
        PlanoDeSaude planoDeSaude = planoDeSaudeService.obterPlanoDeSaudePorId(id);
        return ResponseEntity.ok(convertToDTO(planoDeSaude));
    }

    @GetMapping
    public ResponseEntity<List<PlanoDeSaudeDTO>> listarPlanosDeSaude() {
        List<PlanoDeSaude> planosDeSaude = planoDeSaudeService.listarPlanosDeSaude();
        List<PlanoDeSaudeDTO> planosDTO = planosDeSaude.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planosDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDeSaudeDTO> atualizarPlanoDeSaude(@PathVariable Long id, @Valid @RequestBody PlanoDeSaudeDTO planoDeSaudeDTO) {
        PlanoDeSaude planoDeSaudeAtualizado = planoDeSaudeService.atualizarPlanoDeSaude(id, convertToEntity(planoDeSaudeDTO));
        return ResponseEntity.ok(convertToDTO(planoDeSaudeAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPlanoDeSaude(@PathVariable Long id) {
        planoDeSaudeService.excluirPlanoDeSaude(id);
        return ResponseEntity.noContent().build();
    }

    private PlanoDeSaudeDTO convertToDTO(PlanoDeSaude planoDeSaude) {
        return new PlanoDeSaudeDTO(planoDeSaude.getIdPlano(), planoDeSaude.getNomePlano(), planoDeSaude.getDescricao());
    }

    private PlanoDeSaude convertToEntity(PlanoDeSaudeDTO planoDeSaudeDTO) {
        return new PlanoDeSaude(planoDeSaudeDTO.getIdPlano(), planoDeSaudeDTO.getNomePlano(), planoDeSaudeDTO.getDescricao(), null);
    }
}
