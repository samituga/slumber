package io.samituga.bard.fixture;

import static io.samituga.bard.configuration.ServerConfigBuilder.serverConfigBuilder;
import static io.samituga.bard.fixture.FilterTestData.aFilter;

import io.samituga.bard.configuration.ServerConfigBuilder;

public class ServerConfigTestData {


    public static ServerConfigBuilder aServerConfig() {
        return serverConfigBuilder()
              .port(8080)
              .filter(aFilter().build());
    }
}
