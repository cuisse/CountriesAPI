package io.github.cuisse.countries.repository;

import io.github.cuisse.countries.model.Country;
import io.github.cuisse.json.Json;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

@Repository
public class CountryRepository {

    private static List<Country> countries;

    /**
     * Loads the countries from the JSON file.
     */
    public static void load() {
        synchronized (CountryRepository.class) {
            if (null != countries) {
                return;
            }
            var storage = new ArrayList<Country>();
            try (var input = new ByteArrayInputStream(Files.readAllBytes(ResourceUtils.getFile("classpath:country_list.json").toPath()))) {
                Json.parse(input).object().forEach((name, capital) -> {
                    storage.add(
                            new Country(name, capital.string())
                    );
                });
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
            countries = Collections.unmodifiableList(storage);
        }
    }

    /**
     * Returns all available countries.
     * 
     * @return All available countries.
     */
    public List<Country> all() {
        return countries;
    }

    /**
     * Finds the capital of the provided country.
     * 
     * @param value The country to look for.
     * @return      The country instance if found, otherwise null.
     */
    public Country findCapital(String value) {
        return find(Country::country, value);
    }

    /**
     * Finds the country of the provided capital.
     * 
     * @param value The capital to look for.
     * @return      The country instance if found, otherwise null.
     */
    public Country findCountry(String value) {
        return find(Country::capital, value);
    }

    private Country find(Function<Country, String> supplier, String value) {
        if (value.isBlank() || value.length() < 3 || value.length() > 50) {
            return null;
        }
        int max = 0;
        Country val = null;
        for (Country country : countries) {
            int cur = matches(supplier.apply(country).toLowerCase(), value.toLowerCase());
            if (max < cur) {
                max = cur;
                val = country;
            }
        }
        return val;
    }

    private int matches(String string1, String string2) {
        int length = 0;
        int index  = 0;
        for (int i = 0; i < string1.length(); i++) {
            if (index >= string2.length()) {
                break;
            }
            if (string1.charAt(i) == string2.charAt(index)) {
                if (index == 0) {
                    if (length > 0) {
                        length = 0;
                    }
                }
                length += 1;
                index  += 1;
            } else {
                index = 0;
            }
        }
        return length;
    }

}
