package com.ada.pokemon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pokemon_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;

    @Column(unique = true, nullable = false)
    private Integer idPokeApi; // [cite: 33]

    @Column(unique = true, nullable = false)
    private String name; // [cite: 33]

    private Integer height; // [cite: 33]
    private Integer weight; // [cite: 33]
    private String firstAbility; // [cite: 36]
    private String types; // [cite: 36] (Armazenado como CSV)
    private LocalDateTime cachedAt; // [cite: 37]
    private Boolean favorite = false; // [cite: 80]
    private String note; // [cite: 80]
}