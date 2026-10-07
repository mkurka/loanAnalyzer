package mk.LoanAnalyzer.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface IParallelSupport {

    <T> CompletableFuture<T> createTask(String taskName, Supplier<T> supplier);

    void runAsync(String taskName, Runnable runnable);
}