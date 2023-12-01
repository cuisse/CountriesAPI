package io.github.cuisse.countries.service;

import io.github.cuisse.countries.model.Country;
import io.github.cuisse.countries.repository.CountryRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.cuisse.json.JsonArray;
import io.github.cuisse.json.JsonConverter;
import io.github.cuisse.json.JsonConverters;

@Service
public class CountryService {

    private final CountryRepository countries;

    /**
     * Creates a new service instance.
     *
     * @param countries The repository to use.
     */
    public CountryService(CountryRepository countries) {
        if (null == countries) {
            throw new NullPointerException("countries == null");
        } else {
            this.countries = countries;
        }
    }

    /**
     * Get all countries.
     *
     * @return JsonArray of all countries.
     */
    @Cacheable("countries")
    @SuppressWarnings("unchecked")
    public JsonArray all() {
        var array = new JsonArray();
        var converter = (JsonConverter<Country>) JsonConverters.instance().find(Country.class);
        countries.all().forEach(country -> {
            array.add(converter.convert(country));
        });
        return array;
    }

    /**
     * Find a capital by country.
     *
     * @param country The country to find.
     * @return The capital or null if not found.
     */
    public Country findCapital(String country) {
        return countries.findCapital(country);
    }

    /**
     * Find a country by capital.
     *
     * @param capital The capital to find.
     * @return The country or null if not found.
     */
    public Country findCountry(String capital) {
        return countries.findCountry(capital);
    }

}
