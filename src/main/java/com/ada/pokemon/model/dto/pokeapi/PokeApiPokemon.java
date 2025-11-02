package com.ada.pokemon.model.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiPokemon {
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
    private List<PokeApiAbilitySlot> abilities;
    private List<PokeApiTypeSlot> types;
}