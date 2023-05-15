package io.projectreactor.contextpropagationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		// [CHANGE] Disabled automatic context propagation
		// [CHANGE] No more correlation ID ("cid") accessor
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

}
