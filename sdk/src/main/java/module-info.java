module net.thenextlvl.incidents {
    exports net.thenextlvl.incidents;
    
    requires static org.jetbrains.annotations;
    requires static org.jspecify;
    requires com.google.common;
    requires java.net.http;

    uses net.thenextlvl.incidents.Incidents;
}