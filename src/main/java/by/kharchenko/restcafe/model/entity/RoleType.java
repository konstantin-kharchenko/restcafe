package by.kharchenko.restcafe.model.entity;

import lombok.Getter;

public enum RoleType {
    ROLE_ADMINISTRATOR("ADMINISTRATOR"),
    ROLE_CLIENT("CLIENT");

    @Getter
    private final String name;
    RoleType(String name) {
        this.name = name;
    }
}
