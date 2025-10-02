package net.thenextlvl.incidents;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.Duration;

@NullMarked
public sealed interface Incidents permits IncidentsImpl {
    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new IncidentsImpl.Builder();
    }

    @Contract(mutates = "io")
    void report(String message);

    @Contract(mutates = "io")
    void report(Throwable throwable);

    @Contract(mutates = "io")
    void report(Throwable throwable, @Nullable String message);

    sealed interface Builder permits IncidentsImpl.Builder {
        @Contract(value = "_ -> this", mutates = "this")
        Builder agent(String agent);

        @Contract(value = "_ -> this", mutates = "this")
        Builder server(String server);

        @Contract(value = "_ -> this", mutates = "this")
        Builder timeout(Duration duration);

        @Contract(value = " -> new", pure = true)
        Incidents build();
    }
}
