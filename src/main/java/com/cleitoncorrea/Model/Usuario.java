package com.cleitoncorrea.Model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    
    private UUID id;
    private String nome;
    private int idade;
    private int score;
    private boolean ativo;
    private String pais;
    private Object equipe;
    private List<Object> logs;
}
