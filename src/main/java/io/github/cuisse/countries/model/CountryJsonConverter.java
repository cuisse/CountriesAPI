package io.github.cuisse.countries.model;

import io.github.cuisse.json.JsonConverter;
import io.github.cuisse.json.JsonObject;
import io.github.cuisse.json.JsonString;
import io.github.cuisse.json.JsonType;
import io.github.cuisse.json.JsonValue;

/**
 * Converts a country to and from JSON.
 */
public final class CountryJsonConverter implements JsonConverter<Country> {

    @Override
    public JsonType type() {
        return JsonType.OBJECT;
    }

    @Override
    public JsonValue convert(Country value) {
        JsonObject object = new JsonObject();
        object.put("country", new JsonString(value.country()));
        object.put("capital", new JsonString(value.capital()));
        return object;
    }

    @Override
    public Country convert(JsonValue value) {
        JsonObject object = value.object();
        return new Country(
            object.get("country").string(),
            object.get("capital").string()
        );
    }

}
