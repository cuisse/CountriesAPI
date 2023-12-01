package io.github.cuisse.countries.controller;

import io.github.cuisse.countries.model.Country;
import io.github.cuisse.countries.model.CountryJsonConverter;
import io.github.cuisse.countries.service.CountryService;

import io.github.cuisse.json.JsonArray;
import io.github.cuisse.json.JsonConverters;
import io.github.cuisse.json.JsonObject;
import io.github.cuisse.json.JsonString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CountryControllerTest {

    private final CountryService service = Mockito.mock(CountryService.class);
    private MockMvc mockMvc;

    static {
        JsonConverters.instance().register(Country.class, new CountryJsonConverter());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new CountryController(service)
        ).build();
    }

    @Test
    @DisplayName("Should successfully return all countries")
    void whenRequestingAllCountries_thenSuccess() throws Exception {
        Mockito.when(service.all()).thenReturn(createCountries(10));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/all").accept("application/json")).andDo(MockMvcResultHandlers.log()).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.length()").value(10)
        );
    }

    @Test
    @DisplayName("Should successfully return a country by capital")
    void whenRequestingCountryByCapital_thenSuccess() throws Exception {
        Mockito.when(service.findCountry("Capital 1")).thenReturn(new Country("Country 1", "Capital 1"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/country/Capital 1").accept("application/json")).andDo(MockMvcResultHandlers.log()).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.country").value("Country 1"),
                MockMvcResultMatchers.jsonPath("$.capital").value("Capital 1")
        );
    }

    @Test
    @DisplayName("Should successfully return a capital by country")
    void whenRequestingCapitalByCountry_thenSuccess() throws Exception {
        Mockito.when(service.findCapital("Country 1")).thenReturn(new Country("Country 1", "Capital 1"));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/capital/Country 1").accept("application/json")).andDo(MockMvcResultHandlers.log()).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.country").value("Country 1"),
                MockMvcResultMatchers.jsonPath("$.capital").value("Capital 1")
        );
    }

    private JsonArray createCountries(int count) {
        JsonArray array = new JsonArray();
        for (int i = 0; i < count; i++) {
            JsonObject country = new JsonObject();
            country.put("country", new JsonString("Country " + i));
            country.put("capital", new JsonString("Capital " + i));
            array.add(country);
        };
        return array;
    }

}