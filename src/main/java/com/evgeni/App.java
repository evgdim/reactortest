package com.evgeni;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        buffer();
        flatmap();
        flatmapMono();
        mapVsFlatMap();
        flatmapMonoVsMap();
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
}
