module io.samituga.slumber.malz {
    exports io.samituga.slumber.repository;
    exports io.samituga.slumber.repository.command;
    exports io.samituga.slumber.config.database;
    exports io.samituga.slumber.factory;
    exports io.samituga.slumber.model;

    requires org.jooq;
    requires io.samituga.slumber.heimer;
    requires com.zaxxer.hikari;
}
