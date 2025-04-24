package ru.vktry.controller;

import java.util.List;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vktry.model.Currency;
import ru.vktry.service.Service;

@RequestMapping("/api")
@RestController
public class Controller {
    private final Service service;

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok(this.service.getCurrencies());
    }

    @PostMapping("/currencies")
    public ResponseEntity<Currency> addCurrency(@RequestBody Currency currency) {
        if (currency.getName() != null && currency.getBaseCurrency() != null && currency.getPriceChangeRange() != null) {
            this.service.addCurrency(currency);
            return ResponseEntity.status(HttpStatus.CREATED).body(currency);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currency);
        }
    }

    @GetMapping("/currencies/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String id) {
        return ResponseEntity.ok(this.service.getCurrencyById(id));
    }

    @PutMapping("/currencies/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable String id, @RequestBody Currency currency) {
        return ResponseEntity.ok(this.service.updateCurrency(id, currency));
    }

    @DeleteMapping("/currencies/{id}")
    public ResponseEntity<Currency> deleteCurrency(@PathVariable String id) {
        this.service.deleteCurrencyById(id);
        return ResponseEntity.noContent().build();
    }

    @Generated
    public Controller(final Service service) {
        this.service = service;
    }

}
