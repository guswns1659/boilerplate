package com.titanic.webflux.toby;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFuture - 비동기 작업의 결과를 내가 만들어낼 수 있다. - 리스트의 모든 값이 완료될 때까지 기다릴지 아니면 하나의 값만 완료되길 기다릴지 선택할 수 있다는 것이 장점.
 * CompletionStage - 하나의 비동기작업 수행하고 완료되면 여기에 의존적인 또 다른 작업을 수행할 수 있도록 명령을 가지고 있다.
 * then..Async - Executors를 반영해놓으면 무조건 새로운 쓰레드가 받아서 처리한다.
 */
@Slf4j
public class JackCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture
            .supplyAsync(() -> {
                log.info("runAsync");
                return 1;
            }, es)
            // flatmap의 느낌.
            .thenCompose(s-> {
                log.info("thenCompose = {}", s);
                return CompletableFuture.completedFuture(s + 1);
            })
            // map의 느낌
            .thenApplyAsync(s2-> {
                log.info("thenApplyAsync = {}", s2);
                return s2 * 3;
            }, es)
            // exception이 upstream에서 쭉 넘어온다.
            .exceptionally(e -> -10)
            .thenAcceptAsync(s3 -> log.info("thenAccept = {}", s3), es);

        log.info("exit");

        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
