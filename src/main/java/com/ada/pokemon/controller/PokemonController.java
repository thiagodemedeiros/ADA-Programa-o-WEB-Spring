package com.ada.pokemon.controller;

import com.ada.pokemon.model.dto.PokemonFavoriteRequest;
import com.ada.pokemon.model.dto.PokemonListDto;
import com.ada.pokemon.model.entity.Pokemon;
import com.ada.pokemon.service.PokemonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon") // [cite: 15]
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    // 1. Endpoint de importação/cache
    @PostMapping("/cache/{nameOrId}")
    public ResponseEntity<Pokemon> cachePokemon(@PathVariable String nameOrId) {
        Pokemon cachedPokemon = pokemonService.cachePokemon(nameOrId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cachedPokemon); // [cite: 31]
    }

    // 2. Listagem paginada [cite: 65]
    @GetMapping
    public ResponseEntity<Page<PokemonListDto>> listPokemon(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<PokemonListDto> pokemonPage = pokemonService.listPokemon(pageable);
        return ResponseEntity.ok(pokemonPage);
    }

    // 3. Detalhe [cite: 69]
    @GetMapping("/{idLocal}")
    public ResponseEntity<Pokemon> getPokemonDetail(@PathVariable Long idLocal) {
        Pokemon pokemon = pokemonService.getPokemonDetail(idLocal);
        return ResponseEntity.ok(pokemon); // [cite: 70]
    }

    // 4. Busca por tipo [cite: 72]
    @GetMapping("/search")
    public ResponseEntity<List<Pokemon>> searchPokemonByType(@RequestParam String type) {
        List<Pokemon> pokemonList = pokemonService.searchByType(type);
        return ResponseEntity.ok(pokemonList);
    }

    // 5. Favoritar/nota [cite: 79]
    @PatchMapping("/{idLocal}/favorite")
    public ResponseEntity<Pokemon> favoritePokemon(
            @PathVariable Long idLocal,
            @Valid @RequestBody PokemonFavoriteRequest request) { // [cite: 80]
        Pokemon updatedPokemon = pokemonService.updateFavorite(idLocal, request);
        return ResponseEntity.ok(updatedPokemon);
    }
}