package io.projectreactor.contextpropagationdemo;

import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation();
		// [CHANGE] No more correlation ID ("cid") accessor
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

}
