package io.samituga.slumber.bard.javalin.stub;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class StubPersonDeserializer extends JsonDeserializer<StubPerson> {

    @Override
    public StubPerson deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
          throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        StubPerson person = new StubPerson();
        person.setP1(node.get("name").asText());
        person.setP2(node.get("age").asInt());
        return person;
    }
}
