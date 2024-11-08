package com.example.demo.dto;

import com.example.demo.entity.Dentista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DentistaDTO extends RepresentationModel<DentistaDTO> {

    private Long idDentista;
    private String nomeDentista;
    private String senhaDentista;
    private String especialidade;
    private String cro;

    public DentistaDTO(Dentista dentista) {
        this.idDentista = dentista.getIdDentista();
        this.nomeDentista = dentista.getNomeDentista();
        this.senhaDentista = dentista.getSenhaDentista();
        this.especialidade = dentista.getEspecialidade();
        this.cro = dentista.getCro();
    }
}
