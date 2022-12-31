package io.samituga.bard.configuration;

import io.samituga.bard.filter.Filter;
import io.samituga.slumber.heimer.builder.Builder;
import java.util.Collection;
import java.util.Optional;

public class ServerConfigBuilder implements Builder<ServerConfigImpl> {
    private int port;
    private Optional<Collection<Filter>> filters = Optional.empty();

    public ServerConfigBuilder port(int port) {
        this.port = port;
        return this;
    }

    public ServerConfigBuilder filters(Optional<Collection<Filter>> filters) {
        this.filters = filters;
        return this;
    }

    @Override
    public ServerConfigImpl build() {
        return new ServerConfigImpl(port, filters);
    }

    @Override
    public Builder<ServerConfigImpl> copy(ServerConfigImpl serverConfiguration) {
        return ServerConfig.builder()
              .port(serverConfiguration.port())
              .filters(serverConfiguration.filters());
    }
}
