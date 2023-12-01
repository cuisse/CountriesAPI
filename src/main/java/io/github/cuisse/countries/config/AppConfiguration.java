package io.github.cuisse.countries.config;

import io.github.cuisse.countries.model.Country;
import io.github.cuisse.countries.model.CountryJsonConverter;
import io.github.cuisse.countries.repository.CountryRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.github.cuisse.json.JsonConverters;

@Configuration
@EnableWebMvc
@EnableCaching
public class AppConfiguration {

    /**
     * Register the CountryJsonConverter to the JsonConverters
     *
     * @see JsonConverters
     * @return ApplicationRunner to be run at startup.
     */
    @Bean ApplicationRunner configureJson() {
        return (args) -> {
            JsonConverters.instance().register(Country.class, new CountryJsonConverter());
        };
    }

    /**
     * Load the countries from the JSON file.
     *
     * @return ApplicationRunner to be run at startup.
     */
    @Bean ApplicationRunner configureRepository() {
        return (args) -> {
            CountryRepository.load();
        };
    }

    /**
     * Configure the cache manager to use a ConcurrentMapCacheManager
     *
     * @return CacheManager
     */
    @Bean CacheManager configureCacheManager() {
        return new ConcurrentMapCacheManager("countries");
    }

}
