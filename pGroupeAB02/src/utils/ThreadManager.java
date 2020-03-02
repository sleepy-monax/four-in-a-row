package utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {
    private static ExecutorService service;

    public static Future launchThread(final Runnable runnable) {
        return service.submit(runnable);
    }

    public static void waitForCompletion(final Future future) throws ExecutionException, InterruptedException {
        future.get();
    }

    public static void Initialize() {
        service = Executors.newCachedThreadPool();
    }

    public static void shutdown() {
        System.out.println("Shutting down thread manager...");
        service.shutdownNow();
    }
}
