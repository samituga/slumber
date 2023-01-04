package io.samituga.slumber.bard.javalin;

import io.samituga.bard.application.SlumberApplication;
import io.samituga.bard.configuration.ServerConfig;
import io.samituga.bard.exception.ServerInitException;
import io.samituga.bard.exception.ServerShutdownException;

public class JavalinApplication implements SlumberApplication {

    private boolean isOnline;

    @Override
    public void init(ServerConfig config) {
        if (isOnline) {
            throw new ServerInitException();
        }
    }

    @Override
    public void shutdown() {
        if (!isOnline) {
            throw new ServerShutdownException();
        }
    }
}
