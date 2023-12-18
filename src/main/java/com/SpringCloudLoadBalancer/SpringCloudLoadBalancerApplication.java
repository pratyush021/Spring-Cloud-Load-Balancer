package com.SpringCloudLoadBalancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@SpringBootApplication
public class SpringCloudLoadBalancerApplication {
	private static Logger log = LoggerFactory.getLogger(SpringCloudLoadBalancerApplication.class);
	public static void main(String[] args) {
		System.out.println("test 1");
		SpringApplication.run(SpringCloudLoadBalancerApplication.class, args);
	}

	@GetMapping("/greetings")
	public String greet() {
		log.info("Access /greetings");

		List<String> greetings = Arrays.asList("Greetings", "Aloha", "Salutations");

		Random rand = new Random();
		int randomNum = rand.nextInt(greetings.size());
		return greetings.get(randomNum);

	}
	@GetMapping("/")
	public String home() {
		log.info("Accecss /");
		return "Hi";
	}
}


