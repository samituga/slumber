module slumber.malz {
    exports io.samituga.slumber.malz.repository.provider;
    exports io.samituga.slumber.malz.repository;
    exports io.samituga.slumber.malz.database;
    exports io.samituga.slumber.malz.factory;
    exports io.samituga.slumber.malz.model;
    exports io.samituga.slumber.malz.driver;
    exports io.samituga.slumber.malz.error;

    requires slumber.ivern;
    requires slumber.heimer;
    requires java.sql;
}
