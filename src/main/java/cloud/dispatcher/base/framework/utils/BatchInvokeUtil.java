package cloud.dispatcher.base.framework.utils;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import lombok.NonNull;

public class BatchInvokeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInvokeUtil.class);

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool(
            Runtime.getRuntime().availableProcessors() * 2);

    public int batchInvokeMethod(@NonNull List<Runnable> tasks) {
        Future<Integer> result = forkJoinPool.submit(new CustomTask(tasks));
        try {
            int executedTaskAmount = result.get();
            LOGGER.debug("Executed task amount: {}", executedTaskAmount);
            return executedTaskAmount;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static class CustomTask extends RecursiveTask<Integer> {

        private List<Runnable> taskList;

        public CustomTask(@NonNull List<Runnable> taskList) {
            this.taskList = taskList;
        }

        public CustomTask(@NonNull Runnable task) {
            this.taskList = Lists.newArrayList(task);
        }

        @Override
        protected Integer compute() {
            List<CustomTask> forkList = Lists.newArrayList();
            int result = 0;
            if (taskList.size() == 1) {
                taskList.get(0).run();
                return 1;
            } else {
                for (Runnable task : taskList) {
                    CustomTask customTask = new CustomTask(task);
                    customTask.fork();
                    forkList.add(customTask);
                }
            }
            for (CustomTask task : forkList) {
                result = result + task.join();
            }
            return result;
        }
    }
}
