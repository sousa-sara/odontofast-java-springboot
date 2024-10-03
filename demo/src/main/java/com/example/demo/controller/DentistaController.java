package com.example.demo.controller;

import com.example.demo.dto.DentistaDTO;
import com.example.demo.entity.Dentista;
import com.example.demo.service.DentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
            Dentista novoDentista = dentistaService.criarDentista(convertToEntity(dentistaDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(novoDentista));
        } catch (Exception e) {
            // Log da exceção para análise
            System.err.println("Erro ao criar dentista: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Retorna null em caso de erro
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DentistaDTO> obterDentista(@PathVariable Long id) {
        Optional<Dentista> dentistaOptional = dentistaService.obterDentistaPorId(id);
        return dentistaOptional.map(dentista -> ResponseEntity.ok(convertToDTO(dentista)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DentistaDTO>> listarDentistas() {
        List<Dentista> dentistas = dentistaService.listarDentistas();
        List<DentistaDTO> dentistaDTOs = dentistas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dentistaDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DentistaDTO> atualizarDentista(@PathVariable Long id, @Valid @RequestBody DentistaDTO dentistaDTO) {
        Dentista dentistaAtualizado = dentistaService.atualizarDentista(id, convertToEntity(dentistaDTO));
        return ResponseEntity.ok(convertToDTO(dentistaAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDentista(@PathVariable Long id) {
        dentistaService.excluirDentista(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos de conversão entre DTO e Entidade
    private Dentista convertToEntity(DentistaDTO dto) {
        return new Dentista(
                dto.getIdDentista(),
                dto.getNomeDentista(),
                dto.getSenhaDentista(),
                dto.getEspecialidade(),
                dto.getCro(),
                null
        );
    }

    private DentistaDTO convertToDTO(Dentista entity) {
        return new DentistaDTO(
                entity.getIdDentista(),
                entity.getNomeDentista(),
                entity.getSenhaDentista(),
                entity.getEspecialidade(),
                entity.getCro()
        );
    }
}
