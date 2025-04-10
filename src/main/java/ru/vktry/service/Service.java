package ru.vktry.service;

import ru.vktry.model.Currency;
import ru.vktry.repository.Repository;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class Service {
    private final Repository currencyRepository;

    public Service(Repository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency addCurrency(Currency currency) {
        currency.setId(UUID.randomUUID().toString());
        return currencyRepository.save(currency);
    }

    public Currency getCurrencyById(String id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Не найдена валюта с ID:" + id));
    }

    public Currency updateCurrency(String id, Currency currency) {
        Currency existing = getCurrencyById(id);
        existing.setName(currency.getName());
        existing.setDefaultCurrency(currency.getDefaultCurrency());
        existing.setPriceChangeRange(currency.getPriceChangeRange());
        existing.setDescription(currency.getDescription());
        return currencyRepository.save(existing);
    }

    public void deleteCurrencyById(String id) {
        currencyRepository.deleteById(id);
    }
}