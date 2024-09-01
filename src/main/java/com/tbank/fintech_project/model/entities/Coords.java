package com.tbank.fintech_project.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Coords {
    @JsonProperty
    private double lat;
    @JsonProperty
    private double lon;
}
