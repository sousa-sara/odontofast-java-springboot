package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DentistaDTO {

    private Long idDentista;
    private String nomeDentista;
    private String senhaDentista;
    private String especialidade;
    private String cro;

}
