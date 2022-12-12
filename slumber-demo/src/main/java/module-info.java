module io.samituga.demo {
    requires io.samituga.slumber.malz;
    requires io.samituga.slumber.heimer;
    requires java.sql;
    requires org.jooq;
    requires ch.qos.reload4j;

    exports io.samituga.demo.jooq;
    exports io.samituga.demo.jooq.tables;
    exports io.samituga.demo.jooq.tables.records;
    exports io.samituga.demo.jooq.tables.pojos;
}