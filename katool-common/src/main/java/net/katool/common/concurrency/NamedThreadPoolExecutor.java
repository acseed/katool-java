package net.katool.common.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hongchen.cao
 * @since 16 三月 2021
 */
@Slf4j
public class NamedThreadPoolExecutor extends ThreadPoolExecutor {
    private final String threadPoolName;
    private static final int DEFAULT_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private static final int DEFAULT_QUEUE_SIZE = 10;

    public NamedThreadPoolExecutor(String threadPoolName) {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 1L, TimeUnit.MINUTES, 
                new ArrayBlockingQueue<>(10), 
                new NamedThreadFactory(threadPoolName),
                new WarnRejectedAbortPolicy(threadPoolName + "_Discard"));
        this.threadPoolName = threadPoolName;
    }

    public NamedThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, 
                                   long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, 
                new NamedThreadFactory(threadPoolName), 
                new WarnRejectedAbortPolicy(threadPoolName + "_Discard"));
        this.threadPoolName = threadPoolName;
    }

    public NamedThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, 
                                   long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, 
                                   RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, 
                new NamedThreadFactory(threadPoolName), handler);
        this.threadPoolName = threadPoolName;
    }

    public NamedThreadPoolExecutor(String threadPoolName, int corePoolSize, int maximumPoolSize, 
                                   long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, 
                                   ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (Objects.nonNull(t)) {
            log.error("线程池{}执行任务{}异常: {}", this.threadPoolName, r.getClass().getCanonicalName(), t.getMessage(), t);
        }
    }
}
