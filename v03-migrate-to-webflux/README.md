This module shows how to properly use the scope of Reactor's Context and have 
correlation ids present.

**The goal**: Understand Context scope.

**Steps**:

1. `build.gradle`: Removed `-web` dependency to run `WebFlux` server instead of MVC.
2. Explain: Removed `Filter`, which can be replaced with a `WebFilter` later. But first: explain chained `Mono`s and `Context` scopes
3. Show: We're returning `Mono<String>` instead of `String`.
4. Explain: Need for `Mono.defer()` - framework will subscribe at some point in time.
5. `contextCapture()` -> `contextWrite(Context.of("cid", name))`;
   Describe we now don't see the correlation id in the surrounding `Mono`, just the internal one, due to `Context` scope.
6. Remove `MDC.put()` and move `contextWrite` to surrounding `Mono`.
7. Remove `contextWrite()`, go to `WebFilter` in main class, uncomment `WebFilter`.