package edu.spring.mongorx.controller;

import edu.spring.mongorx.utils.StackPublisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rx")
@CrossOrigin("*")
public class ReactiveController {

    private final StackPublisher stackPublisher;

    public ReactiveController(StackPublisher stackPublisher) {
        this.stackPublisher = stackPublisher;
    }

    @PostMapping("/pub/{text}")
    public void pub(@PathVariable String text) {
        stackPublisher.publish(text);
    }

    @GetMapping(value = "/sub")
    public Flux<String> sub() {
        return stackPublisher.subscribe();
    }

    @GetMapping(value = "/values")
    public List<String> values() {
        return stackPublisher.getValues();
    }

    @DeleteMapping(value = "/reset")
    public void resetPubSub() {
        stackPublisher.reset();
    }

}
