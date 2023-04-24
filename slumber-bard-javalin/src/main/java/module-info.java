module slumber.bard.javalin {
    exports io.samituga.slumber.bard.javalin;
    exports io.samituga.slumber.bard.javalin.exception;

    requires slumber.bard;
    requires slumber.ivern;
    requires slumber.jayce;
    requires io.javalin;
    requires jakarta.servlet;
    requires kotlin.stdlib;
    requires com.fasterxml.jackson.databind;
}
