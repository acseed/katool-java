package net.katool.common.collection;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 分页迭代器
 * @author hongchen.cao
 * @since 16 三月 2021
 */
public abstract class AbstractPageIterable<T>{
    private final int DEFAULT_PAGE_SIZE = 1000;
    private final int DEFAULT_START = 0;

    private int pageSize;
    private int start;
    public AbstractPageIterable(int pageSize) {
        this.pageSize = pageSize;
        this.start = DEFAULT_START;
    }
    public AbstractPageIterable(int start, int pageSize) {
        this.start = start;
        this.pageSize = pageSize;
    }

    private class PageIterator implements Iterator<Collection<T>> {
        private Collection<T> pageData;
        private int off = start;
        private boolean doNext = true;

        @Override
        public boolean hasNext() {
            if (!doNext) {
                return false;
            }
            pageData = nextPage(off, pageSize);
            if (CollectionUtils.isNotEmpty(pageData)) {
                off += pageData.size();
                if (pageData.size() < pageSize) {
                    doNext = false;
                }
                return true;
            }
            return false;
        }

        @Override
        public Collection<T> next() {
            if (Objects.isNull(pageData)) {
                throw new NoSuchElementException();
            }
            return pageData;
        }

        @Override
        public void remove() {
            throw new RuntimeException("不可变集合");
        }
    }


    public Iterable<T> getAll() {
        return DoubleDeckIterable.create((Iterable<Collection<T>>) PageIterator::new);
    }

    /**
     * 下一页
     * @param off 
     * @param pageSize
     * @return
     */
    protected abstract List<T> nextPage(int off, int pageSize);
}
