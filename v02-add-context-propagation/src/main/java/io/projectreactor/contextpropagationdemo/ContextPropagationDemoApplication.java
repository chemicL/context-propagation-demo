package io.projectreactor.contextpropagationdemo;

import io.micrometer.context.ContextRegistry;
import jakarta.servlet.Filter;
import org.slf4j.MDC;
import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		// TODO
		// [CHANGE] Added accessor:
		ContextRegistry.getInstance().registerThreadLocalAccessor(
				"cid",
				() -> MDC.get("cid"),
				cid -> MDC.put("cid", cid),
				() -> MDC.remove("cid"));
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

	@Bean
	Filter correlationFilter() {
		return (request, response, chain) -> {
			String name = request.getParameter("name");
			if (name != null) {
				MDC.put("cid", name);
			}
			chain.doFilter(request, response);
		};
	}
}
