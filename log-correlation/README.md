This module is designed to show the most exciting features of automatic context
propagation with Reactor.

**The goal**: Learn how to manually configure log correlation.

**Steps**:

1. Prepare.
   - Launch $JAVA_HOME/bin/jwebserver using JDK18+.
2. See it fail.
   - Run the app and `curl "localhost:8080/webClient?name=Eva"`.
   - Notice the correlation id gets lost.
3. Make it work.
   - build.gradle: Add `context-propagation` library.
   - main: Register a `ThreadLocalAccessor`.
   - main: Enable automatic context propagation.
   - Run and `curl "localhost:8080/webClient?name=Alice"`.
4. Try a reactive endpoint. 
   - controller: change signature to `Mono<String>`.
   - controller: Remove `block()`.
   - Run and `curl "localhost:8080/webClient?name=Bob"`.
5. Convert to WebFlux.
   - build.gradle: Remove `-web` dependency and refresh to run `WebFlux`.
   - main: Replace `Filter` with a `WebFilter`.
   - Run and `curl "localhost:8080/webClient?name=Carol"`.
6. Use Observation from Micrometer to correlate logs.
   - main: Remove `WebFilter`.
   - main: Remove `ThreadLocalAccessor`.
   - application.properties: Change log format to work with trace-id and span-id.
   - build.gradle: Replace `context-propagation` library with 
     `io.micrometer:micrometer-tracing-bridge-otel`.