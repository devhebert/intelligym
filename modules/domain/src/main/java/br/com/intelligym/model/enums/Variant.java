package br.com.intelligym.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Variant {
    A("Completo"),
    B("Foco em um grupo muscular"),
    C("Foco em mais de um grupo muscular"),
    D("Completo e cardio");

    private String description;

    Variant(String description) {
        this.description = description;
    }
}
