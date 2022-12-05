module io.samituga.slumber.malz {
    exports io.samituga.slumber.repository;
    exports io.samituga.slumber.config.database;
    exports io.samituga.slumber.factory;

    requires org.jooq;
    requires io.samituga.slumber.heimer;
    requires com.zaxxer.hikari;
}
