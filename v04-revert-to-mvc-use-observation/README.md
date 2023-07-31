This module uses Micrometer's `Observation` natively supported by Spring.

**The goal**: Show how Observation from Micrometer is transparently accessible. 

**Steps**:

1. `build.gradle`: Back to MVC (`-web` dependency back).
2. `build.gradle`: Remove explicit `context-propagation` dependency - `micrometer-tracing-bridge-*` adds it.
3. Explain: When we add tracing bridge, tracing hooks into the `Observation` lifecycle.
4. Explain: Tracing also exposes trace-id and span-id in `MDC`
5. `application.properties`: Show log format without cid.
6. `main`: `ThreadLocalAccessor` removed, using `Observation` started by Spring Framework.
7. Explain: Controller has no logic for capturing or writing to `Context`, just plain app 
   logic and logging where appropriate.
8. Optionally: remove `-web` dependency from `build.gradle`, refresh project, and demonstrate the `WebFlux` version works properly too.