package io.projectreactor.contextpropagationdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

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
		this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/")
		                                 .build();
	}

	@GetMapping("/webClient")
	// [CHANGE] Back to String. However, MVC also handles Mono/Flux signatures! (below)
	// [CHANGE] No more "name" param
	String webClient() {
		// [CHANGE] Back to imperative code
		// [CHANGE] No more "cid"
		log.info("webClient endpoint called");
		return webClient.get()
		                .uri("/HELP.md")
		                .retrieve()
		                .toEntity(String.class)
		                .doOnNext(entity -> log.info("Response status: {}", entity.getStatusCode()))
		                .mapNotNull(HttpEntity::getBody)
		                // [CHANGE] calling block, which automatically captures
		                .block();
	}

	@GetMapping("/webClientReactive")
	Mono<String> webClientReactive() {
		log.info("webClient endpoint called");
		return webClient.get()
		                .uri("/HELP.md")
		                .retrieve()
		                .toEntity(String.class)
		                .doOnNext(entity -> log.info("Response status: {}", entity.getStatusCode()))
		                .mapNotNull(HttpEntity::getBody);
		                // not calling block in reactive endpoints
	}
}