package ru.vktry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.vktry.model.Currency;

@org.springframework.stereotype.Service
public class Service {
    private final List<Currency> currencies = new ArrayList();

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public Currency addCurrency(Currency currency) {
        String id = UUID.randomUUID().toString();
        currency.setId(id);
        this.currencies.add(currency);
        return currency;
    }

    public Currency getCurrencyById(String id) {
        return (Currency)this.currencies.stream().filter((currency) -> {
            return currency.getId().equals(id);
        }).findFirst().orElseThrow(() -> {
            return new RuntimeException("Не найдена валюта с ID:" + id);
        });
    }

    public Currency updateCurrency(String id, Currency currency) {
        Currency updatedCurrency = this.getCurrencyById(id);
        updatedCurrency.setName(currency.getName());
        updatedCurrency.setDefaultCurrency(currency.getDefaultCurrency());
        updatedCurrency.setPriceChangeRange(currency.getPriceChangeRange());
        updatedCurrency.setDescription(currency.getDescription());
        return updatedCurrency;
    }

    public void deleteCurrencyById(String id) {
        this.currencies.remove(this.getCurrencyById(id));
    }

}
