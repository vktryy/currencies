package ru.vktry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vktry.model.Currency;

public interface Repository extends JpaRepository<Currency, String> {
}