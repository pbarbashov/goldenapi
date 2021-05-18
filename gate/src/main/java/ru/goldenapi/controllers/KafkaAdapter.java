package ru.goldenapi.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gate")
public class KafkaAdapter {
    @PostMapping("")
    public Mono<String> routeMessageToKafka(@RequestBody String message) {
        return Mono.just("");
    }

}
