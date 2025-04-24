package ru.vktry.service;


import jakarta.annotation.PostConstruct;
import org.springframework.web.client.RestTemplate;
import ru.vktry.model.Currency;
import ru.vktry.repository.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@org.springframework.stereotype.Service
public class Service {

    private final Repository currencyRepository;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    public Service(Repository currencyRepository, RestTemplate restTemplate) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = restTemplate;
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
        existing.setBaseCurrency(currency.getBaseCurrency());
        existing.setPriceChangeRange(currency.getPriceChangeRange());
        existing.setDescription(currency.getDescription());
        return currencyRepository.save(existing);
    }

    public void deleteCurrencyById(String id) {
        currencyRepository.deleteById(id);
    }

    @PostConstruct
    public void checkCurrencyChanges() {
        Map<String, Object> currencies = getCurrenciesFromApi();

        for (Currency currency : getCurrencies()) {
            String charCode = currency.getBaseCurrency();

            if (currencies.containsKey(charCode)) {
                Map<String, Object> currencyData = (Map<String, Object>) currencies.get(charCode);

                double value = Double.parseDouble(currencyData.get("Value").toString());
                double previous = Double.parseDouble(currencyData.get("Previous").toString());
                double priceChangeRange = Double.parseDouble(currency.getPriceChangeRange());

                double changePercentage = (previous - value) / previous * 100;

                if (Math.abs(changePercentage) >= priceChangeRange) {
                    System.out.println(currency.getName() + " изменилась на " + changePercentage + "%");
                }
            }
        }
    }

    private Map<String, Object> getCurrenciesFromApi() {
        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        return (Map<String, Object>) response.get("Valute");
    }
}
