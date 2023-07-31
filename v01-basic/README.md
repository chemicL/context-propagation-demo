This module just shows you around, no code changes are required.

**The goal**: Learn how log correlation id gets lost
when transitioning to a reactive chain that transparently switches Threads. 

**Steps**:

1. build.gradle: Show dependencies.
2. application.properties: Show log format.
3. Show `Filter`.
4. Run and depict how correlation id gets lost when context is switched after 
   `WebClient` response is delivered.