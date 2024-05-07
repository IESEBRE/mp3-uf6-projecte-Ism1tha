package com.insebre.project.model;

import java.util.*;

public class SuperCollection<T> implements List<T>, Set<T> {

    public enum CollectionType {
        ARRAY_LIST,
        TREE_SET
    }

    private CollectionType type;
    private Collection<T> collection; // Use Collection for flexibility

    public SuperCollection(CollectionType type) {
        this.type = type;
        switch (type) {
            case ARRAY_LIST:
                this.collection = new ArrayList<>();
                break;
            case TREE_SET:
                this.collection = new TreeSet<>();
                break;
            default:
                throw new IllegalArgumentException("Unsupported collection type");
        }
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        Collection<T> newCollection;
        switch (type) {
            case ARRAY_LIST:
                newCollection = new ArrayList<>();
                break;
            case TREE_SET:
                newCollection = new TreeSet<>();
                break;
            default:
                throw new IllegalArgumentException("Unsupported collection type");
        }
        newCollection.addAll(collection);
        collection = newCollection;
        this.type = type;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return collection.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        collection.clear();
    }

    // List-specific methods (supported only if type is ARRAY_LIST)
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).addAll(index, c);
        } else {
            throw new UnsupportedOperationException("addAll(int index, Collection<? extends T> c) is not supported for Set");
        }
    }

    @Override
    public T get(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).get(index);
        } else {
            throw new UnsupportedOperationException("get(int index) is not supported for Set");
        }
    }

    @Override
    public T set(int index, T element) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).set(index, element);
        } else {
            throw new UnsupportedOperationException("set(int index, T element) is not supported for Set");
        }
    }

    @Override
    public void add(int index, T element) {
        if (type == CollectionType.ARRAY_LIST) {
            ((List<T>) collection).add(index, element);
        } else {
            throw new UnsupportedOperationException("add(int index, T element) is not supported for Set");
        }
    }

    @Override
    public T remove(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).remove(index);
        } else {
            throw new UnsupportedOperationException("remove(int index) is not supported for Set");
        }
    }

    @Override
    public int indexOf(Object o) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).indexOf(o);
        } else {
            throw new UnsupportedOperationException("indexOf(Object o) is not supported for Set");
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).lastIndexOf(o);
        } else {
            throw new UnsupportedOperationException("lastIndexOf(Object o) is not supported for Set");
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).listIterator();
        } else {
            throw new UnsupportedOperationException("listIterator() is not supported for Set");
        }
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).listIterator(index);
        } else {
            throw new UnsupportedOperationException("listIterator(int index) is not supported for Set");
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).subList(fromIndex, toIndex);
        } else {
            throw new UnsupportedOperationException("subList(int fromIndex, int toIndex) is not supported for Set");
        }
    }

    // Unsupported methods for Set<T>
    // Throwing UnsupportedOperationException for Set-specific methods
    @Override
    public Spliterator<T> spliterator() {
        return collection.spliterator();
    }
}
