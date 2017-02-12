package com.evgeni;

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
        subscribersMultyTread();
        System.in.read();
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
