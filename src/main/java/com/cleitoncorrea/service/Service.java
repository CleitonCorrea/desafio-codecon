package com.cleitoncorrea.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cleitoncorrea.Model.InsightEquipe;
import com.cleitoncorrea.Model.Log;
import com.cleitoncorrea.Model.Usuario;
import com.cleitoncorrea.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    
    
    private  final UsuarioRepository usuarioRepository;

    /*
     GET /superusers
        filtro: score >= 900
        filtro: ativo = true
        Retorna os dados e o tempo da requisição
     */
    public List<Usuario> superUsuarios(){
        return usuarioRepository.findAll().parallelStream()
                .filter(usuario -> usuario.getScore() >= 900)
                .filter(usuario -> usuario.isAtivo())
                .toList();
    }

    /*
     GET / top-countries
     Agrupa os super usuários por país.
     Retorna os 5 países com maior número de super usuários.
     */

     public List<Map<String, Object>> topCountries(){
        return superUsuarios().parallelStream()
                .collect(Collectors.groupingBy(Usuario::getPais, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
                .map(e-> Map.<String, Object>of("pais", e.getKey(), "total", e.getValue()))
                .toList();
            }
    
    /*
        GET /team-insights 
        Agrupa por team.name.
        Retorna: total de membros, líderes, projetos concluídos e % de membros ativos.
    */

    public List<InsightEquipe> insightEquipes() {
        return usuarioRepository.findAll().parallelStream()
                .collect(Collectors.groupingBy(u -> u.getEquipe().getNome()))
                .entrySet().stream()
                .map(e -> {

                    var usuarios = e.getValue();
                    int membros = usuarios.size();
                    int lideres = (int) usuarios.stream().filter(u -> u.getEquipe().isLider()).count();
                    int concluidos = usuarios.stream()
                            .flatMap(u -> u.getEquipe().getProjetos().stream())
                            .mapToInt(p -> p.isConcluido() ? 1 : 0)
                            .sum();
                    int ativos = (int) usuarios.stream().filter(Usuario::isAtivo).count();
                    double pct = membros == 0 ? 0 : (ativos * 100.0) / membros;

                    return new InsightEquipe(e.getKey(), membros, lideres, concluidos, pct);

                }).toList();

    }

    /*
        GET /active-users-per-day
        Conta quantos logins aconteceram por data
        Query param opcional: ?min=300 para filtrar com pelo menos 3.000 logins.
    */
    
   public Map<LocalDate, Long> loginPorDia(int min){
    return usuarioRepository.findAll().parallelStream()
    .flatMap(u-> u.getLogs().stream())
    .filter(l->"login".equalsIgnoreCase(l.getAcao()))
    .collect(Collectors.groupingBy(Log::getData, Collectors.counting()))
    .entrySet().stream()
    .filter(e-> e.getValue() >= min)
    .sorted()
    .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (e1, e2) -> e1,
        LinkedHashMap::new
    ));
   } 

}
