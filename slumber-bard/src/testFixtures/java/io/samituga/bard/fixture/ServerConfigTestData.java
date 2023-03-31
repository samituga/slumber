package io.samituga.bard.fixture;

import static io.samituga.bard.configuration.ServerConfigBuilder.serverConfigBuilder;
import static io.samituga.bard.fixture.FilterTestData.aFilter;

import io.samituga.bard.configuration.ServerConfigBuilder;
import io.samituga.bard.type.Port;

public class ServerConfigTestData {


    public static ServerConfigBuilder aServerConfig() {
        return serverConfigBuilder()
              .port(Port.of(8080))
              .filter(aFilter().build());
    }
}
