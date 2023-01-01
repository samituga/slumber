module slumber.malz.jooq {
    exports io.samituga.slumber.malz.jooq.repository;
    exports io.samituga.slumber.malz.jooq.config;
    exports io.samituga.slumber.malz.jooq.factory;

    requires io.samituga.slumber.heimer;
    requires io.samituga.slumber.malz;
    requires org.jooq;
    requires com.zaxxer.hikari;
}