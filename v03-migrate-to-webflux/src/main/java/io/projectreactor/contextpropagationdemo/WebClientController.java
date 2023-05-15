package io.projectreactor.contextpropagationdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class WebClientController {

	private static final Logger log = LoggerFactory.getLogger(WebClientController.class);

	private final WebClient webClient;

	public WebClientController(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/").build();
	}

	@GetMapping("/webClient")
	Mono<String> webClient(@RequestParam String name) {
		// [CHANGE] Returning Mono instead of calling imperative code
		return Mono.defer(() -> {
			MDC.put("cid", name); // TODO: Do we need to write to MDC and capture?
			log.info("webClient endpoint called");
			return webClient.get()
			                .uri("/HELP.md")
			                .retrieve()
			                .toEntity(String.class)
			                .doOnNext(entity -> log.info("Response status: {}", entity.getStatusCode()))
			                .mapNotNull(HttpEntity::getBody)
			                .contextCapture(); // TODO: Can we avoid writing to MDC and
											   //       immediately reading the value?
							// [CHANGE] Not calling block()
		})
				// TODO: Can the outer Mono's (defer) Context be used?
				;
	}
}
