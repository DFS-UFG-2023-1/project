package br.ufg.nocurriculum.entities;

import lombok.Getter;

@Getter
public enum Roles {

    USER("Sou usuário"),
    COMPANY("Sou empresa");

    private final String description;

    Roles(String description) {
        this.description = description;
    }

}
