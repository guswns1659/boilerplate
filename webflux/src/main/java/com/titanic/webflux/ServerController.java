package com.titanic.webflux;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ServerController {

    private final KitchenService kitchenService;

    @GetMapping(value = "/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Dish> serveDishes() {
        return kitchenService.getDishes();
    }

}

@Service
class KitchenService {

    Flux<Dish> getDishes() {
        return Flux.<Dish> generate(sink -> sink.next(new Dish())).delayElements(Duration.ofMillis(250));
    }
}

class Dish {

}
