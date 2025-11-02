package com.ada.pokemon.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PokemonFavoriteRequest {
    @NotNull(message = "O campo 'favorite' é obrigatório")
    private Boolean favorite; // [cite: 80]
    private String note; // [cite: 80]
}