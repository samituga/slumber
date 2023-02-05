module slumber.malz.jooq {
    exports io.samituga.slumber.malz.jooq.repository;
    exports io.samituga.slumber.malz.jooq.config;
    exports io.samituga.slumber.malz.jooq.factory;

    requires slumber.heimer;
    requires slumber.ivern;
    requires slumber.malz;
    requires org.jooq;
    requires com.zaxxer.hikari;
}