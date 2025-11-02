package com.ada.pokemon.model.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiTypeSlot {
    private PokeApiType type;
    private Integer slot;
}