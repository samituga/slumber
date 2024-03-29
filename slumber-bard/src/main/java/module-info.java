module slumber.bard {
    exports io.samituga.bard.application;
    exports io.samituga.bard.configuration;
    exports io.samituga.bard.endpoint;
    exports io.samituga.bard.endpoint.context;
    exports io.samituga.bard.endpoint.request;
    exports io.samituga.bard.endpoint.request.type;
    exports io.samituga.bard.endpoint.response;
    exports io.samituga.bard.endpoint.response.type;
    exports io.samituga.bard.endpoint.route;
    exports io.samituga.bard.exception;
    exports io.samituga.bard.filter;
    exports io.samituga.bard.filter.type;
    exports io.samituga.bard.handler;
    exports io.samituga.bard.type;
    exports io.samituga.bard.path;

    requires slumber.heimer;
    requires slumber.ivern;
    requires slumber.jayce;
    requires jakarta.servlet;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
}
