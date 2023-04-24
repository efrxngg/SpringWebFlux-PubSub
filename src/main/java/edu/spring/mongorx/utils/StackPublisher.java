package edu.spring.mongorx.utils;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;

@Component
public class StackPublisher {

    private final List<String> values = new ArrayList<>();
    private Sinks.Many<String> emitter;

    public StackPublisher() {
        this.emitter = Sinks.many().replay().all();
    }

    public void publish(String value) {
        emitter.tryEmitNext(value).orThrow();
        values.add(value);
    }

    public List<String> getValues() {
        return values;
    }

    public void reset() {
        emitter.tryEmitComplete();
        values.clear();
        emitter = Sinks.many().replay().all();
    }

    public Flux<String> subscribe() {
        return emitter.asFlux()
                .doOnSubscribe(x -> System.out.println("Sub: " + x))
                .doOnNext(x -> System.out.println("Emi: " + x))
                .doFinally(x -> System.out.println("Fin: " + x))
                .doOnComplete(()-> System.out.println("Completed"))
                .doOnCancel(() -> System.out.println("Canceled"));
    }

}
