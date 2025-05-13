package com.cleitoncorrea.Model;

import java.util.List;

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
public class Equipe {
    
    private String nome;
    private boolean lider;
    private List<Projeto> projetos;
}
