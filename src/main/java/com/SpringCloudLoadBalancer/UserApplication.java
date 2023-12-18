package com.SpringCloudLoadBalancer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class UserApplication {
    private final WebClient.Builder loadBalancedClientBuilder;
//
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public UserApplication(WebClient.Builder loadBalancedClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedClientBuilder = loadBalancedClientBuilder;
        this.lbFunction = lbFunction;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @GetMapping("hi")
    public Mono<String> hi(@RequestParam(value="name", defaultValue="Mary") String name) {
        return
                loadBalancedClientBuilder.build().get().uri(" http://localhost:8080/greetings")
                        .retrieve().bodyToMono(String.class)
                        .map(greeting -> String.format("%s, %s!", greeting, name));
    }

    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam(value = "name", defaultValue = "john") String name) {
        return WebClient. builder()
                .filter(lbFunction)
                .build().get().uri(" http://localhost:8080/")
                .retrieve().bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }
//
//    public UserApplication(WebClient.Builder webClientBuilder, ReactorLoadBalancerExchangeFilterFunction lbFunction ) {
//
//    }
}
