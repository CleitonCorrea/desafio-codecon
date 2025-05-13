package com.cleitoncorrea.controller;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cleitoncorrea.Model.Usuario;
import com.cleitoncorrea.repository.UsuarioRepository;
import com.cleitoncorrea.service.Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final Service service;

    //POST /usuarios

    @PostMapping("/users")
    public ResponseEntity<?> carregar(@RequestBody List<Usuario> usuarios) {
        try {
            usuarioRepository.saveAll(usuarios);
            return ResponseEntity.ok(Map.of("message", "Arquivo carregado com sucesso!",
            "total_usuarios", usuarioRepository.findAll().size()));
        } catch (Exception e) {
            return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR).body("Erro ao carregar usuários: " + e.getMessage());
        }

    }
}
