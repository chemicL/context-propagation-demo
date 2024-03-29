package io.projectreactor.contextpropagationdemo;

import io.micrometer.context.ContextRegistry;
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
		Hooks.enableAutomaticContextPropagation();
		ContextRegistry.getInstance().registerThreadLocalAccessor(
				"cid",
				() -> MDC.get("cid"),
				cid -> MDC.put("cid", cid),
				() -> MDC.remove("cid"));
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

//	@Bean
//	// [CHANGE] Filter -> WebFilter
//	WebFilter correlationFilter() {
//		return (exchange, chain) -> {
//			var names = exchange.getRequest().getQueryParams().get("name");
//			if (names != null && names.size() > 0) {
//				return chain.filter(exchange)
//				            .contextWrite(Context.of("cid", names.get(0)));
//			}
//			return chain.filter(exchange);
//		};
//	}
}
