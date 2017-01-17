package com.evgeni;

import reactor.core.publisher.Flux;

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
    }

    private static void buffer() {
        Flux.fromArray(new String[]{"aa", "bb", "cc", "dd", "aaa", "bbb", "ccc", "ddd"})
                .buffer(3)
                .subscribe(e -> System.out.println(e));
    }
}
