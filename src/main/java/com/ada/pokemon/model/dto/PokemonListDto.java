package com.ada.pokemon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO para o requisito de listagem paginada [cite: 66]
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonListDto {
    private Long idLocal;
    private Integer idPokeApi;
    private String name;
    private String types;
    private LocalDateTime cachedAt;
}