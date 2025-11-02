package com.ada.pokemon.service;

import com.ada.pokemon.exception.ResourceNotFoundException;
import com.ada.pokemon.model.dto.PokemonFavoriteRequest;
import com.ada.pokemon.model.dto.PokemonListDto;
import com.ada.pokemon.model.dto.pokeapi.PokeApiPokemon;
import com.ada.pokemon.model.dto.pokeapi.PokeApiTypeSlot;
import com.ada.pokemon.model.entity.Pokemon;
import com.ada.pokemon.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final RestTemplate restTemplate;

    @Value("${pokeapi.base-url}")
    private String pokeApiBaseUrl;

    @Transactional
    public Pokemon cachePokemon(String nameOrId) {
        // 1. Busca na PokeAPI [cite: 11, 30]
        String url = pokeApiBaseUrl + "pokemon/" + nameOrId.toLowerCase();
        PokeApiPokemon apiPokemon = restTemplate.getForObject(url, PokeApiPokemon.class);

        if (apiPokemon == null) {
            //
            throw new ResourceNotFoundException("Pokémon não encontrado na PokeAPI: " + nameOrId);
        }

        // 2. Verifica se já existe no H2 pelo idPokeApi [cite: 38]
        Pokemon pokemon = pokemonRepository.findByIdPokeApi(apiPokemon.getId())
                .orElse(new Pokemon()); // Cria um novo se não existir

        // 3. Mapeia e atualiza os dados
        mapApiToEntity(apiPokemon, pokemon);

        // 4. Salva/Atualiza no H2 [cite: 30]
        return pokemonRepository.save(pokemon);
    }

    @Transactional(readOnly = true)
    public Page<PokemonListDto> listPokemon(Pageable pageable) {
        // [cite: 65]
        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageable);
        // Mapeia para o DTO de listagem [cite: 66]
        return pokemonPage.map(this::mapEntityToListDto);
    }

    @Transactional(readOnly = true)
    public Pokemon getPokemonDetail(Long idLocal) {
        // [cite: 70]
        return pokemonRepository.findById(idLocal)
                .orElseThrow(() -> new ResourceNotFoundException("Pokémon não encontrado com id local: " + idLocal)); //
    }

    @Transactional(readOnly = true)
    public List<Pokemon> searchByType(String type) {
        // [cite: 73]
        return pokemonRepository.findByTypesContainingIgnoreCase(type);
    }

    @Transactional
    public Pokemon updateFavorite(Long idLocal, PokemonFavoriteRequest request) {
        // [cite: 80]
        Pokemon pokemon = getPokemonDetail(idLocal); // Reutiliza o método que já trata o 404
        pokemon.setFavorite(request.getFavorite());
        pokemon.setNote(request.getNote());
        return pokemonRepository.save(pokemon);
    }

    // --- Métodos Auxiliares ---

    private void mapApiToEntity(PokeApiPokemon apiPokemon, Pokemon pokemon) {
        // Mapeamento dos campos mínimos
        pokemon.setIdPokeApi(apiPokemon.getId());
        pokemon.setName(apiPokemon.getName());
        pokemon.setHeight(apiPokemon.getHeight());
        pokemon.setWeight(apiPokemon.getWeight());

        // Pega a primeira ability [cite: 36]
        String firstAbility = apiPokemon.getAbilities().stream()
                .findFirst()
                .map(abilitySlot -> abilitySlot.getAbility().getName())
                .orElse(null);
        pokemon.setFirstAbility(firstAbility);

        // Concatena os tipos em CSV [cite: 36]
        String types = apiPokemon.getTypes().stream()
                .map(PokeApiTypeSlot::getType)
                .map(pokeApiType -> pokeApiType.getName())
                .collect(Collectors.joining(","));
        pokemon.setTypes(types);

        pokemon.setCachedAt(LocalDateTime.now()); // Atualiza o timestamp [cite: 37, 38]
    }

    private PokemonListDto mapEntityToListDto(Pokemon pokemon) {
        // Mapeia para o DTO com campos específicos da listagem [cite: 66]
        return new PokemonListDto(
                pokemon.getIdLocal(),
                pokemon.getIdPokeApi(),
                pokemon.getName(),
                pokemon.getTypes(),
                pokemon.getCachedAt()
        );
    }
}