package com.tbank.fintech_project.model.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {
    @JsonProperty
    private String slug;
    @JsonProperty
    private Coords coords;
}
