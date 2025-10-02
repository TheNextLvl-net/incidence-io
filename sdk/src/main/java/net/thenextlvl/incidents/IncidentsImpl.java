package net.thenextlvl.incidents;

import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

@NullMarked
record IncidentsImpl(
        HttpClient client,
        String server,
        String agent,
        Duration timeout
) implements Incidents {
    @Override
    public void report(String message) {
        report(new String[]{message});
    }

    @Override
    public void report(Throwable throwable) {
        report(throwable, null);
    }

    @Override
    public void report(Throwable throwable, @Nullable String message) {
        // todo: implement
    }

    private void report(String[] report) {
        client.sendAsync(HttpRequest.newBuilder()
                        .uri(URI.create(server))
                        .timeout(timeout)
                        .POST(HttpRequest.BodyPublishers.ofString(String.join("\n", report)))
                        .build(),
                HttpResponse.BodyHandlers.discarding());
    }

    static final class Builder implements Incidents.Builder {
        private @Nullable String agent = null;
        private @Nullable String server = null;
        private Duration timeout = Duration.ofSeconds(5);

        @Override
        public Incidents.Builder agent(String agent) {
            this.agent = agent;
            return this;
        }

        @Override
        public Incidents.Builder server(String server) {
            this.server = server;
            return this;
        }

        @Override
        public Incidents.Builder timeout(Duration duration) {
            this.timeout = duration;
            return this;
        }

        @Override
        public Incidents build() {
            Preconditions.checkArgument(agent != null, "Agent must be set");
            Preconditions.checkArgument(server != null, "Server must be set");
            return new IncidentsImpl(
                    HttpClient.newBuilder()
                            .connectTimeout(timeout)
                            .executor(Executors.newSingleThreadExecutor())
                            .version(HttpClient.Version.HTTP_1_1)
                            //.sslContext(SSLContext.getDefault())
                            .build(),
                    server, agent, timeout
            );
        }
    }
}
