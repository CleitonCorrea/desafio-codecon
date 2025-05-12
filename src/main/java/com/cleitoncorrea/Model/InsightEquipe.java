package com.cleitoncorrea.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsightEquipe {
    private String equipe;
    private int totalMembros;
    private int lideres;
    private int projetosConcluidos;
    private Double percentualAtivo;

}
