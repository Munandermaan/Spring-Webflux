import net.bytebuddy.description.field.FieldList;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Example {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .concatWith(Flux.just("Bumrah"))
//                .concatWith(Flux.error(new RuntimeException()))
                .log();
        stringFlux.subscribe(System.out::println, throwable -> System.err.println(throwable),
                () -> System.out.println("Completed"));
    }

    @Test
    public void flux_map() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .map(name -> name.toUpperCase());
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void flux_flatMap() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul");
       Flux<Flux<String>> stringMono = Flux.just(Flux.just("bumrah", "virat"));
       stringMono.subscribe(stringFlux1 -> stringMono.flatMap(s -> {System.out.println(s);
           return s;
       }));

       StepVerifier.create(stringMono.log());
    }

    @Test
    public void flux_OnErrorResume() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .concatWith(Flux.error(new RuntimeException()))
                .onErrorResume((e)-> {
                    System.out.println("Exception is:" + e);
                    return Flux.just("default");
                });

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("Virat", "Rohit", "Rahul")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void flux_OnErrorContinue() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .concatWith(Flux.error(new RuntimeException()))
                .onErrorContinue(( (throwable, o) -> System.out.println("Exception is "+ throwable)));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("Virat", "Rohit", "Rahul")
                .verifyError();
    }

    @Test
    public void flux_Delay() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .delayElements(Duration.ofSeconds(2));
        Thread.sleep(2000L);
        stringFlux.subscribe(s -> System.out.println(s));

    }

    @Test
    public void flux_DoONError() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .concatWith(Flux.error(new RuntimeException()))
                .doOnError(e-> System.out.println("Exception is" + e));
        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("Virat", "Rohit", "Rahul")
                .verifyError();
    }

    @Test
    public void flux_then() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul")
                .flatMap(name -> Flux.range(1, 10)
                        .doOnNext(nr -> System.out.println(("Number: {}" + nr)))
                        .then(Mono.just(name)));

        stringFlux.subscribe(name -> System.out.println("Name" + name));
    }

    @Test
    public void Mono_thenReturn() {
        Mono<String> stringMono = Mono.just("Virat")
                .thenReturn("Bumrah");
        stringMono.subscribe(name -> System.out.println(name));
    }

    @Test
    public void flux_zipWith() {
        Flux<String> stringFlux = Flux.just("Virat", "Rohit", "Rahul");
        Flux<String> stringFlux1 = Flux.just("Bumrah ", "Shami ", "Kuldeep ");
        stringFlux1 = stringFlux1.zipWith(stringFlux , (name1 , name2) -> name1.concat(name2));

        stringFlux1.subscribe(name -> System.out.println(name));
    }

    @Test
    public void mono_switchIfEmpty() {
        Mono<String> stringMono = Mono.empty();
       Mono<String>stringMono1 = stringMono.switchIfEmpty( Mono.just("Rohit"));
       stringMono1.subscribe(name-> System.out.println(name), error -> System.out.println(error), ()-> System.out.println("india"));


    }
}
