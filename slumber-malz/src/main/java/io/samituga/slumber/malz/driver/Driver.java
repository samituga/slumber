package io.samituga.slumber.malz.driver;

public enum Driver {
    POSTGRES("org.postgresql.Driver", 5432);

    public final String className;
    public final int defaultPort;

    Driver(String name, int defaultPort) {
        this.className = name;
        this.defaultPort = defaultPort;
    }
}
