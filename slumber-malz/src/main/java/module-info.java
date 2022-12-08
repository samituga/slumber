module io.samituga.slumber.malz {
    exports io.samituga.slumber.malz.repository;
    exports io.samituga.slumber.malz.repository.operation;
    exports io.samituga.slumber.malz.database;
    exports io.samituga.slumber.malz.factory;
    exports io.samituga.slumber.malz.model;
    exports io.samituga.slumber.malz.repository.provider;

    requires org.jooq;
    requires io.samituga.slumber.heimer;
    requires com.zaxxer.hikari;
}
