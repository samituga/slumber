package io.samituga.jayce;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collection;

public class Json {

    private static final ObjectMapper OBJECT_MAPPER = new JsonMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private static boolean initialized = false;

    public static void initWithModules(Collection<Module> modules) {
        if (initialized) {
            throw new IllegalStateException("Json object was already initialized");
        }
        OBJECT_MAPPER.registerModules(modules);
        initialized = true;
    }

    public static ObjectMapper mapper() {
        if (!initialized) {
            initialized = true;
        }
        return OBJECT_MAPPER;
    }
}
