package net.katool.common.collection;

import java.util.Iterator;

/**
 * @author hongchen.cao
 * @since 16 三月 2021
 */

public class DoubleDeckIterable<E> implements Iterable<E> {
    private final Iterable<? extends Iterable<? extends E>> startContainer;

    protected DoubleDeckIterable(Iterable<? extends Iterable<? extends E>> startContainer) {
        this.startContainer = startContainer;
    }

    public static <E> DoubleDeckIterable<E> create(Iterable<? extends Iterable<? extends E>> src) {
        return new DoubleDeckIterable<>(src);
    }

    @Override public Iterator<E> iterator() {
        return new InnerIterator();
    }

    class InnerIterator implements Iterator<E> {

        private Iterator<? extends Iterable<? extends E>> startIterator;
        private Iterator<? extends E> contentIterator;

        @Override
        public boolean hasNext() {
            if (contentIterator != null && contentIterator.hasNext()) {
                return true;
            }
            if (startIterator == null) {
                this.startIterator = startContainer.iterator();
            }
            while (true) {
                if (startIterator.hasNext()) {
                    contentIterator = startIterator.next().iterator();
                } else {
                    return false;
                }
                if (contentIterator.hasNext()) {
                    return true;
                }
            }
        }

        @Override public E next() {
            return contentIterator.next();
        }

        @Override
        public void remove() {
            throw new RuntimeException("不可删除集合");
        }
    }
}

