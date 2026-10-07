package mk.LoanAnalyzer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
public class ParallelSupport implements IParallelSupport {

    private final Logger logger = LoggerFactory.getLogger(ParallelSupport.class);

    public ParallelSupport() {
    }

    @Async
    public <T> CompletableFuture<T> createTask(String taskName, Supplier<T> supplier) {
        long rnd = MathUtil.randomLong();
        logTaskStart(taskName, rnd);

        long start = System.currentTimeMillis();
        try {
            T result = supplier.get();

            logTaskFinish(taskName, rnd, System.currentTimeMillis() - start);

            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            logTaskFailed(taskName, rnd, System.currentTimeMillis() - start, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async
    public void runAsync(String taskName, Runnable runnable) {
        long rnd = MathUtil.randomLong();
        logTaskStart(taskName, rnd);
        long start = System.currentTimeMillis();

        try {
            runnable.run();
            logTaskFinish(taskName, rnd, System.currentTimeMillis() - start);
        } catch (Exception e) {
            logTaskFailed(taskName, rnd, System.currentTimeMillis() - start, e);
        }
    }

    private void logTaskStart(String taskName, long rnd) {
        logger.debug("Starting task {}-{}", taskName, rnd);
    }

    private void logTaskFinish(String taskName, long rnd, long durationInMilliseconds) {
        logger.debug("Task {}-{} finished in {} ms", taskName, rnd, durationInMilliseconds);
    }

    private void logTaskFailed(String taskName, long rnd, long durationInMilliseconds, Throwable throwable) {
        logger.warn("Task {}-{} failed in {} ms", taskName, rnd, durationInMilliseconds, throwable);
    }
}