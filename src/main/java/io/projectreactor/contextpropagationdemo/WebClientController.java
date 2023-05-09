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
	private final ObservationRegistry observationRegistry;

	public WebClientController(WebClient.Builder webClientBuilder,
			ObservationRegistry observationRegistry) {
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/").build();
		this.observationRegistry = observationRegistry;
	}

	@GetMapping("/webClient")
	String webClient() {
		MDC.put("cid", "correlation");
		log.info("webClient endpoint called");
		return webClient.get()
		                .uri("/HELP.md")
		                .retrieve()
		                .toEntity(String.class)
		                .mapNotNull(ResponseEntity::getBody)
		                .flatMapMany(b -> Flux.fromStream(b.lines()))
		                .flatMap(line -> Mono.just(line)
		                                     .<String>handle((l, s) -> {
			                                     log.info("Next line: {}", l);
			                                     s.next(l);
		                                     })
		                                     .tap(Micrometer.observation(
				                                     observationRegistry)))
		                .last()
//		                .doOnNext(entity -> log.info("Response status: {}", entity.getStatusCode()))
//		                .mapNotNull(HttpEntity::getBody)
                        .contextCapture()
                        .block();
	}
}
