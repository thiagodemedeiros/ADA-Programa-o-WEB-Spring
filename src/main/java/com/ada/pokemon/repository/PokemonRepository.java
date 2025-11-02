package com.ada.pokemon.repository;

import com.ada.pokemon.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    // Usado para verificar se o pokémon já existe no cache pelo ID da PokeAPI
    Optional<Pokemon> findByIdPokeApi(Integer idPokeApi);

    // Usado para buscar pelo tipo (case-insensitive) [cite: 73]
    List<Pokemon> findByTypesContainingIgnoreCase(String type);
}