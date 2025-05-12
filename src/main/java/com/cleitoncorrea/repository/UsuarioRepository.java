package com.cleitoncorrea.repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.cleitoncorrea.Model.Usuario;

@Component
public class UsuarioRepository {

    private final Map<UUID, Usuario> db = new ConcurrentHashMap<>();

    public void saveAll(Collection<Usuario> usuarios) {
        usuarios.forEach(usuario -> {
            db.put(usuario.getId(), usuario);
        });
    }

    public Collection<Usuario> findAll() {
        return db.values();
    }
    
}
