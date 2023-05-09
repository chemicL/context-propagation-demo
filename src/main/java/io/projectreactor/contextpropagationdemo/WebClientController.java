package io.projectreactor.contextpropagationdemo;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	String webClient() {
		MDC.put("cid", "correlation");
		log.info("webClient endpoint called");
		return webClient.get()
		                .uri("/HELP.md")
		                .retrieve()
		                .toEntity(String.class)
		                .doOnNext(entity -> log.info("Response status: {}", entity.getStatusCode()))
		                .mapNotNull(HttpEntity::getBody)
                        .block();
	}
}
