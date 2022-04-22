package net.katool.common.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hongchen.cao
 * @since 16 三月 2021
 */
@Slf4j
public class WarnRejectedAbortPolicy implements RejectedExecutionHandler {
    private final String monitorKey;

    public WarnRejectedAbortPolicy(String monitorKey) {
        this.monitorKey = monitorKey;
    }

    /**
     * @param r
     * @param executor
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("线程池满， 线程被扔掉， monitorKey={}, largestPoolSize={}," +
                "maximumPoolSize={},corePoolSize={}, activeCount={}, completedTaskCount={}", 
                this.monitorKey, executor.getLargestPoolSize(), executor.getMaximumPoolSize(), 
                executor.getCorePoolSize(), executor.getActiveCount(), executor.getCompletedTaskCount());
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                executor.toString());
    }
}
