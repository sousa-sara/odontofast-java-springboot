package com.example.demo.controller;
import com.example.demo.dto.PlanoDeSaudeDTO;
import com.example.demo.entity.PlanoDeSaude;
import com.example.demo.service.interfaces.PlanoDeSaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
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
        try {
            PlanoDeSaude novoPlanoDeSaude = planoDeSaudeService.criarPlanoDeSaude(convertToEntity(planoDeSaudeDTO));
            PlanoDeSaudeDTO dtoResponse = convertToDTO(novoPlanoDeSaude);
            adicionarLinks(dtoResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        } catch (Exception e) {
            System.err.println("Erro ao criar plano de saúde: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDeSaudeDTO> obterPlanoDeSaude(@PathVariable Long id) {
        PlanoDeSaude planoDeSaude = planoDeSaudeService.obterPlanoDeSaudePorId(id);
        PlanoDeSaudeDTO dtoResponse = convertToDTO(planoDeSaude);
        adicionarLinks(dtoResponse);
        return ResponseEntity.ok(dtoResponse);
    }

    @GetMapping
    public ResponseEntity<List<PlanoDeSaudeDTO>> listarPlanosDeSaude() {
        List<PlanoDeSaude> planosDeSaude = planoDeSaudeService.listarPlanosDeSaude();
        List<PlanoDeSaudeDTO> planosDTO = planosDeSaude.stream()
                .map(this::convertToDTO)
                .peek(this::adicionarLinks)
                .collect(Collectors.toList());
        return ResponseEntity.ok(planosDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDeSaudeDTO> atualizarPlanoDeSaude(@PathVariable Long id, @Valid @RequestBody PlanoDeSaudeDTO planoDeSaudeDTO) {
        PlanoDeSaude planoDeSaudeAtualizado = planoDeSaudeService.atualizarPlanoDeSaude(id, convertToEntity(planoDeSaudeDTO));
        PlanoDeSaudeDTO dtoResponse = convertToDTO(planoDeSaudeAtualizado);
        adicionarLinks(dtoResponse);
        return ResponseEntity.ok(dtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPlanoDeSaude(@PathVariable Long id) {
        planoDeSaudeService.excluirPlanoDeSaude(id);
        return ResponseEntity.noContent().build();
    }

    // Método para adicionar links HATEOAS ao DTO
    private void adicionarLinks(PlanoDeSaudeDTO planoDeSaudeDTO) {
        if (planoDeSaudeDTO != null) {
            Long id = planoDeSaudeDTO.getIdPlano();
            planoDeSaudeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlanoDeSaudeController.class).obterPlanoDeSaude(id)).withSelfRel());
            planoDeSaudeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlanoDeSaudeController.class).atualizarPlanoDeSaude(id, planoDeSaudeDTO)).withRel("atualizar"));
            planoDeSaudeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlanoDeSaudeController.class).listarPlanosDeSaude()).withRel("listarPlanos"));
            planoDeSaudeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlanoDeSaudeController.class).excluirPlanoDeSaude(id)).withRel("excluir"));
        }
    }

    private PlanoDeSaudeDTO convertToDTO(PlanoDeSaude planoDeSaude) {
        return new PlanoDeSaudeDTO(planoDeSaude.getIdPlano(), planoDeSaude.getNomePlano(), planoDeSaude.getDescricao());
    }

    private PlanoDeSaude convertToEntity(PlanoDeSaudeDTO planoDeSaudeDTO) {
        return new PlanoDeSaude(planoDeSaudeDTO.getIdPlano(), planoDeSaudeDTO.getNomePlano(), planoDeSaudeDTO.getDescricao(), null);
    }
}
