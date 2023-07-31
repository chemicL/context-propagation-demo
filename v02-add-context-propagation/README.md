This module shows how to use the context-propagation library to keep correlation id 
across `Thread` boundaries in reactive pipelines in a blocking setup (Spring MVC).

**The goal**: Learn the basics of Reactor context propagation. 

**Steps**:

1. `build.gradle`: New dependency added.
2. `main` method: `ThreadLocalAccessor` added.
3. `WebClient` chain uses `handle()` operator.
4. Use `.contextCapture()` to make correlation id appear in `handle()` lambda.
5. Use `Hooks.enableAutomaticContextPropagation()`.
6. Remove `contextCapture()` - `block()` automatically captures in automatic mode.