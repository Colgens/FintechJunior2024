package com.tbank.fintech_project.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coords {
    private double lat;
    private double lon;
}
