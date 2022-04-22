package net.katool.common.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.function.Function;

/**
 * 某个数据集想分批处理，得到另一个数据集
 * @author hongchen.cao
 * @since 22 四月 2022
 */
public class BatchCollector<T, R> {
    private final int batchSize;
    private final List<T> dataList;
    private Function<List<T>, List<R>> mapFunc;
    
    private static final int DEFAULT_BATCH_SIZE = 1000;

    public BatchCollector(int batchSize, List<T> dataList) {
        this.batchSize = batchSize;
        this.dataList = dataList;
    }

    public BatchCollector(List<T> dataList) {
        this(DEFAULT_BATCH_SIZE, dataList);
    }

    public List<R> collect() {
        if (CollectionUtils.isEmpty(dataList)) {
            return Lists.newArrayList();
        }
        List<List<T>> partitions = Lists.partition(this.dataList, this.batchSize);
        List<R> result = Lists.newArrayList();
        for (List<T> partition : partitions) {
            List<R> targetList = this.mapFunc.apply(partition);
            result.addAll(targetList);
        }
        return result;
    }

    public BatchCollector<T, R> map(Function<List<T>, List<R>> mapFunc) {
        this.mapFunc = mapFunc;
        return this;
    }
}

