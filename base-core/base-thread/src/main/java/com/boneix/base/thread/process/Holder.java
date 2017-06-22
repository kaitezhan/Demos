package com.boneix.base.thread.process;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author wangchong
 */
public final class Holder<T> {
    private CountDownLatch latch;

    private Future<T> future;

    Holder(CountDownLatch latch, Future<T> future) {
        super();
        this.latch = latch;
        this.future = future;
    }

    public T getResult() {
        try {
            latch.await();
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public T getResult(long timeout) {
        try {
            latch.await(timeout, TimeUnit.MILLISECONDS);
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
