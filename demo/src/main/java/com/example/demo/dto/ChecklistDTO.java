package com.example.demo.dto;

import com.example.demo.entity.Checklist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistDTO extends RepresentationModel<ChecklistDTO>  {

    private Long idChecklist;
    private Integer escovacaoDentes;
    private Integer fioDental;
    private Integer enxaguanteBucal;
    private Long usuarioIdUsuario;

}
