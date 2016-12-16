package com.evgeni;

import reactor.core.publisher.Flux;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        Flux.fromArray(new String[]{"asd", "bbb"}).subscribe(e -> System.out.println(e));
    }
}
