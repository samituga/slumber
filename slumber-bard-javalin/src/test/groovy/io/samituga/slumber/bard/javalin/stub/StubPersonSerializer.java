package io.samituga.slumber.bard.javalin.stub;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class StubPersonSerializer extends JsonSerializer<StubPerson> {
    @Override
    public void serialize(StubPerson value, JsonGenerator gen, SerializerProvider serializers)
          throws IOException {
            gen.writeStartObject();
            gen.writeStringField("name", value.getP1());
            gen.writeNumberField("age", value.getP2());
            gen.writeEndObject();
    }
}
