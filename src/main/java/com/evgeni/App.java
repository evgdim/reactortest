package com.evgeni;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BlockingSink;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
//        buffer();
//        flatmap();
//        flatmapMono();
//        mapVsFlatMap();
//        flatmapMonoVsMap();
        //subscribers();
        //subscribersMultyTread();
        //resumeOnError();
        backpressure();
        System.in.read();
    }

    private static void backpressure() {
        Flux<Integer> rangeFlux = Flux.range(1, 10)
                .map(i -> i * 5);

        Subscriber s = new Subscriber() {
            private Subscription sub;
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.sub = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext "+ (Integer)o);
                this.sub.request(5);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        rangeFlux.subscribe(s);

    }

    private static void resumeOnError() {
        Flux.fromArray(new Integer[]{1, 2, 3, 4 ,5, 6, 0, 7, 8, 9, 0})
                .flatMap(i -> {
                    try {
                        return Flux.just(100 / i);
                    } catch (Exception e) {
                        return Flux.empty();
                    }
                })
                .subscribe(e -> System.out.println(e));
    }

    private static void buffer() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .buffer(3)
                .subscribe(e -> System.out.println(e));
    }

    private static void flatmap() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .flatMap(str -> Flux.fromArray(str.split("")))
                .distinct()
                .subscribe(e -> System.out.println(e));
    }
    private static void flatmapMono() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .flatMap(str -> Mono.fromSupplier(() -> str))
                .distinct()
                .subscribe(e -> System.out.println(e));
    }
    private static void mapVsFlatMap() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .map(str -> Flux.fromArray(str.split("")))
                .subscribe(e -> System.out.println(e));
    }
    private static void flatmapMonoVsMap() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .map(str -> Mono.fromSupplier(() -> str))
                .distinct()
                .subscribe(e -> System.out.println(e));
    }
    private static void subscribers() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .subscribeOn(Schedulers.parallel())
                .subscribe(e -> System.out.println(e + " " + Thread.currentThread().getName()));
    }

    private static void subscribersMultyTread() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd", "eee","fff","ggg", "hhh", "iii", "jjj", "kkk","aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd", "eee","fff","ggg", "hhh", "iii", "jjj", "kkk"})
                .flatMap(str -> Mono.just(str).subscribeOn(Schedulers.parallel()).map(s -> s + "_123"))
                .subscribe(e -> System.out.println(e + " " + Thread.currentThread().getName()));
    }
}
