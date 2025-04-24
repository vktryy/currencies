package ru.vktry.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDTO {
    private String name;
    private String charCode;
    private double value;
    private double previous;
}
