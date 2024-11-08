package com.example.demo.service.interfaces;

import com.example.demo.dto.ChecklistDTO;

import java.util.List;

public interface ChecklistService {

    ChecklistDTO criarChecklist(ChecklistDTO checklistDTO);
    ChecklistDTO obterChecklistPorId(Long id);
    List<ChecklistDTO> listarChecklists();
    ChecklistDTO atualizarChecklist(Long id, ChecklistDTO checklistDTO);
    boolean excluirChecklist(Long id);

}
