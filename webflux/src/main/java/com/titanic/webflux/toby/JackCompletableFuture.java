package com.titanic.webflux.toby;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * CompletableFuture
 *  - 비동기 작업의 결과를 내가 만들어낼 수 있다.
 *  - 리스트의 모든 값이 완료될 때까지 기다릴지 아니면 하나의 값만 완료되길 기다릴지 선택할 수 있다는 것이 장점.
 * CompletionStage
 *  - 하나의 비동기작업 수행하고 완료되면 여기에 의존적인 또 다른 작업을 수행할 수 있도록 명령을 가지고 있다.
 */
@Slf4j
public class JackCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture
            .runAsync(() -> log.info("runAsync"))
            .thenRun(() -> log.info("thenRun"))
            .thenRun(() -> log.info("thenRun"));

        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
