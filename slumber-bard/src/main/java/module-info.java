module slumber.bard {
    exports io.samituga.bard.application;
    exports io.samituga.bard.configuration;
    exports io.samituga.bard.filter;
    exports io.samituga.bard.filter.type;
    exports io.samituga.bard.exception;
    exports io.samituga.bard.endpoint;
    exports io.samituga.bard.endpoint.type;
    exports io.samituga.bard;
    exports io.samituga.bard.endpoint.response;
    exports io.samituga.bard.endpoint.request;
    exports io.samituga.bard.endpoint.response.type;

    requires jakarta.servlet;
    requires slumber.heimer;
    requires slumber.ivern;
}
