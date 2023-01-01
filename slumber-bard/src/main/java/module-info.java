module slumber.bard {
    exports io.samituga.bard.application;
    exports io.samituga.bard.configuration;
    exports io.samituga.bard.filter;
    exports io.samituga.bard.exception;
    exports io.samituga.bard.type;

    requires jakarta.servlet;
    requires slumber.heimer;
    requires slumber.ivern;
}
