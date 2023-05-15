package io.projectreactor.contextpropagationdemo;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.MDC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContextPropagationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContextPropagationDemoApplication.class, args);
	}

	@Bean
	Filter correlationFilter() {
		return (request, response, chain) -> {
			String name = request.getParameter("name");
			if (name != null) {
				MDC.put("cid", name);
			}
			chain.doFilter(request, response);
		};
	}

}
