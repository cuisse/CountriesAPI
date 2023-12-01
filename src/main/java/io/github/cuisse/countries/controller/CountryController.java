package io.github.cuisse.countries.controller;

import java.util.Optional;

import io.github.cuisse.countries.model.Country;
import io.github.cuisse.countries.service.CountryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import io.github.cuisse.json.Json;

@RestController
public class CountryController {

    private final CountryService service;

    /**
     * Creates a new controller instance.
     * 
     * @param service The service to use.
     */
    public CountryController(CountryService service) {
        if (service == null) {
            throw new NullPointerException();
        } else {
            this.service = service;
        }
    }

    @RequestMapping(path = "/v1/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> all() {
        return ResponseEntity.ok(
            Json.json(service.all(), false)
        );
    }

    @RequestMapping(path = "/v1/capital/{value}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> capital(@PathVariable String value) {
        return get(
            service.findCapital(value)
        );
    }

    @RequestMapping(path = "/v1/country/{value}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> country(@PathVariable String value) {
        return get(
            service.findCountry(value)
        );
    }

    private ResponseEntity<Object> get(Country country) {
        if (country == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.of(Optional.of(
                Json.json(country, false))
            );
        }
    }

}
