package io.projectreactor.contextpropagationdemo;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		// [CHANGE] Added accessor:
		ContextRegistry.getInstance().registerThreadLocalAccessor(
				"cid",
				() -> MDC.get("cid"),
				cid -> MDC.put("cid", cid),
				() -> MDC.remove("cid"));
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

}
