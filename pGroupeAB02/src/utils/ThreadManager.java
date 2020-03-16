package utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {
    private static ExecutorService service;

    public static void initialize() {
        service = Executors.newCachedThreadPool();
    }

    public static void shutdown() {
        service.shutdownNow();
    }

    public static Future launch(final Runnable runnable) {
        return service.submit(runnable);
    }

    public static void wait(final Future future) throws ExecutionException, InterruptedException {
        future.get();
    }
}
