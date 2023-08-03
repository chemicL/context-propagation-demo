package io.projectreactor.contextpropagationdemo;

import io.micrometer.context.ContextRegistry;
import jakarta.servlet.Filter;
import org.slf4j.MDC;
import reactor.core.publisher.Hooks;
import reactor.util.context.Context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		// TODO: How to make all operators see the modified MDC regardless of Thread
		//  switches?

		// TODO: How to propagate MDC across Thread boundaries?
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

	// TODO: How to make a filter for WebFlux?
	@Bean
	Filter correlationFilter() {
		return (request, response, chain) -> {
			try {
				String name = request.getParameter("name");
				if (name != null) {
					MDC.put("cid", name);
				}
				chain.doFilter(request, response);
			} finally {
				MDC.remove("cid");
			}
		};
	}

}
