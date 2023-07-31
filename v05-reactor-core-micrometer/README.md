This module shows advanced use of Micrometer's Observation via Reactor's integration.

**The goal**: Show `Micrometer.observation()` to spawn Observations around a reactive chain.

1. `build.gradle`: We added `reactor-core-micrometer` module.
2. Explain: We need an `ObservationRegistry` bean injected into our controller.
3. Explain: We’re streaming lines from the file.
4. Explain: We’re attaching an `Observation` to `Mono` using `tap(Micrometer.
   observation())` to observe each line independently using a different tracing span.
5. Explain: When parent `Observation` is in scope, the above will create child `Observation`s
6. Explain: We need to capture current `Observation` into the Context for this 
   relationship to work. This is no longer necessary, as `block` operator automatically 
   captures, and a reactive chain has an `Observation` attached to the `Context`.