package com.ada.pokemon.model.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiAbilitySlot {
    private PokeApiAbility ability;
    private Integer slot;
}