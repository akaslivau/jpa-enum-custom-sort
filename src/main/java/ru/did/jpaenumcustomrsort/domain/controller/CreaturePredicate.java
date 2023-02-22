package ru.did.jpaenumcustomrsort.domain.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreaturePredicate {


    @JsonProperty("name")
    private String name;

    @JsonProperty("kinds")
    private List<String> kinds;

    @JsonProperty("weightFrom")
    private Long weightFrom;

    @JsonProperty("weightTo")
    private Long weightTo;

}
