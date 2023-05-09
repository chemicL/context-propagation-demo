package io.projectreactor.contextpropagationdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private static final Logger log = LoggerFactory.getLogger(ExampleController.class);

	@GetMapping("/hello")
	String hello() {
		MDC.put("cid", "correlation");
		log.info("hello endpoint called");
		return "Hello!";
	}
}
