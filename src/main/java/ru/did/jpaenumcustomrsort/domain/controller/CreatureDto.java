package ru.did.jpaenumcustomrsort.domain.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatureDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("legCount")
    private Integer legCount;

    @JsonProperty("weight")
    private Double weight;
}
