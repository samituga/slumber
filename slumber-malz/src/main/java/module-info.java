module io.samituga.slumber.malz {
    exports io.samituga.slumber.malz.repository;
    exports io.samituga.slumber.malz.database;
    exports io.samituga.slumber.malz.factory;
    exports io.samituga.slumber.malz.model;
    exports io.samituga.slumber.malz.repository.provider;
    exports io.samituga.slumber.malz.driver;
    exports io.samituga.slumber.malz.error;

    requires io.samituga.slumber.heimer;
    requires java.sql;
}
