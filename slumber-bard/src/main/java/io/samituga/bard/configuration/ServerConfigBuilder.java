package io.samituga.bard.configuration;

import io.samituga.slumber.heimer.builder.Builder;

public class ServerConfigBuilder implements Builder<ServerConfigImpl> {
    private int port;

    public ServerConfigBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public ServerConfigImpl build() {
        return new ServerConfigImpl(port);
    }

    @Override
    public Builder<ServerConfigImpl> copy(ServerConfigImpl serverConfiguration) {
        return ServerConfig.builder()
              .port(serverConfiguration.port());
    }
}
