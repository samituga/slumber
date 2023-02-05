module slumber.rakan {
    exports io.samituga.slumber.rakan.support;
    exports io.samituga.slumber.rakan.support.post;

    requires slumber.heimer;
    requires slumber.ivern;
    requires java.net.http;
//    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
}