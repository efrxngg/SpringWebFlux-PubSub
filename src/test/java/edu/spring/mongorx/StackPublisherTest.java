package edu.spring.mongorx;


import edu.spring.mongorx.utils.StackPublisher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.stream.IntStream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StackPublisherTest {

    @Autowired
    private StackPublisher stackPublisher;

    @BeforeAll
    public void setup() {
        stackPublisher.publish("Hola");
    }

    @Test
    @Order(1)
    void subscriberOne() {
        System.out.println("Subscribe 1");
        stackPublisher.subscribe()
                .subscribe(a -> System.out.println("Sub1: " + a), Throwable::printStackTrace);
    }

    @Test
    void subscriberTwo() {
        System.out.println("Subscribe 2");
        stackPublisher.subscribe()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(a -> System.out.println("Sub2: " + a), Throwable::printStackTrace);
    }

    @Test
    @Order(2)
    void sendData() {
        IntStream.range(1, 5).forEach(i -> stackPublisher.publish(String.valueOf(i)));
    }

}
