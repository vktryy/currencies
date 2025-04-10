package ru.vktry.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    private String id;
    private String name;
    private String defaultCurrency;
    private String priceChangeRange;
    private String description;
}