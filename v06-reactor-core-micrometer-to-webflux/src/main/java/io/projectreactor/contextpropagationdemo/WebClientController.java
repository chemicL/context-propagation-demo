package io.projectreactor.contextpropagationdemo;

import java.util.function.Function;

import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class WebClientController {

	private static final Logger log = LoggerFactory.getLogger(WebClientController.class);

	private final WebClient webClient;

	private final ObservationRegistry observationRegistry;

	public WebClientController(WebClient.Builder webClientBuilder,
			ObservationRegistry registry) {
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/")
		                                 .build();
		observationRegistry = registry;
	}

	@GetMapping("/webClient")
	// [CHANGE] Mono response
	Mono<String> webClient() {
		return Mono.defer(() -> {
			log.info("webClient endpoint called");
			return webClient.get()
			                .uri("/HELP.md")
			                .retrieve()
			                .toEntityFlux(String.class)
			                .mapNotNull(HttpEntity::getBody)
			                .flatMapMany(Function.identity())
			                .flatMap(line -> Mono.just(line)
			                                     .<String>handle((l, s) -> {
				                                     log.info("Next line: {}", l);
				                                     s.next(l);
			                                     })
			                                     .tap(Micrometer.observation(
					                                     observationRegistry)))
			                .collect(StringBuilder::new,
					                (sb, line) -> sb.append(line)
					                                .append("\n"))
			                .map(StringBuilder::toString);
							// [CHANGE] not calling .block()
		});
	}
}