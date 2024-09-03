package com.tbank.fintech_project.model.entities;


import lombok.Data;

@Data
public class Location {
    private String slug;
    private Coords coords;
}
