package com.insebre.project.model;

import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.ExistingElementTreeSetException;

import java.io.Serializable;
import java.util.*;

/**
 * A flexible collection implementation that can behave like ArrayList or TreeSet based on its type.
 *
 * @param <T> The type of elements stored in the collection.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class SuperCollection<T> implements List<T>, Set<T>, Serializable {

    /**
     * Enumeration representing the types of collections supported.
     */
    public enum CollectionType {
        ARRAY_LIST,
        TREE_SET
    }

    private CollectionType type;
    private Collection<T> collection; // Use Collection for flexibility

    /**
     * Constructs a new SuperCollection with the specified type.
     *
     * @param type The type of collection to be instantiated (ArrayList or TreeSet).
     * @throws IllegalArgumentException if an unsupported collection type is provided.
     */
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

    /**
     * Retrieves the type of collection.
     *
     * @return The type of collection (ArrayList or TreeSet).
     */
    public CollectionType getType() {
        return type;
    }

    /**
     * Sets the type of collection.
     *
     * @param type The new type of collection (ArrayList or TreeSet).
     * @throws IllegalArgumentException if an unsupported collection type is provided.
     */
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
        if (type == CollectionType.ARRAY_LIST) {
            return collection.add(e);
        } else {
            if (collection.add(e)) {
                return true;
            } else {
                ExceptionController.handleException(new ExistingElementTreeSetException());
                return false;
            }
        }
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
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
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

    @Override
    public boolean equals(Object o) {
        return collection.equals(o);
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }

    @Override
    public T get(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            if (index >= 0 && index < collection.size()) {
                return ((List<T>) collection).get(index);
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else if (type == CollectionType.TREE_SET) {
            if (index < 0 || index >= collection.size()) {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }

            // Iterate through the TreeSet to find the element at the specified index
            Iterator<T> iterator = collection.iterator();
            int currentIndex = 0;
            while (iterator.hasNext()) {
                T element = iterator.next();
                if (currentIndex == index) {
                    return element;
                }
                currentIndex++;
            }

            // If the index is out of bounds (should not reach here normally)
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else {
            throw new UnsupportedOperationException("get(int index) is not supported for this collection type");
        }
    }


    @Override
    public T set(int index, T element) {
        if (collection instanceof List) {
            return ((List<T>) collection).set(index, element);
        } else {
            throw new UnsupportedOperationException("set(int index, T element) is not supported for Set");
        }
    }

    @Override
    public void add(int index, T element) {
        if (collection instanceof List) {
            ((List<T>) collection).add(index, element);
        } else {
            throw new UnsupportedOperationException("add(int index, T element) is not supported for Set");
        }
    }

    @Override
    public T remove(int index) {
        if (collection instanceof List) {
            return ((List<T>) collection).remove(index);
        } else {
            throw new UnsupportedOperationException("remove(int index) is not supported for Set");
        }
    }

    @Override
    public int indexOf(Object o) {
        if (collection instanceof List) {
            return ((List<T>) collection).indexOf(o);
        } else {
            throw new UnsupportedOperationException("indexOf(Object o) is not supported for Set");
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        if (collection instanceof List) {
            return ((List<T>) collection).lastIndexOf(o);
        } else {
            throw new UnsupportedOperationException("lastIndexOf(Object o) is not supported for Set");
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        if (collection instanceof List) {
            return ((List<T>) collection).listIterator();
        } else {
            throw new UnsupportedOperationException("listIterator() is not supported for Set");
        }
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (collection instanceof List) {
            return ((List<T>) collection).listIterator(index);
        } else {
            throw new UnsupportedOperationException("listIterator(int index) is not supported for Set");
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (collection instanceof List) {
            return ((List<T>) collection).subList(fromIndex, toIndex);
        } else {
            throw new UnsupportedOperationException("subList(int fromIndex, int toIndex) is not supported for Set");
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return collection.spliterator();
    }

    /**
     * Updates an element at the specified index with a new element.
     *
     * @param index         The index of the element to update.
     * @param updatedElement The new element to replace the existing element at the index.
     */
    public void customSet(int index, T updatedElement) {
        if (type == CollectionType.ARRAY_LIST) {
            if (index >= 0 && index < collection.size()) {
                ((List<T>) collection).set(index, updatedElement);
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else if (type == CollectionType.TREE_SET) {
            // Create a new TreeSet to hold the updated elements
            TreeSet<T> newSet = new TreeSet<>();
            int currentIndex = 0;
            boolean updated = false;

            for (T element : collection) {
                if (currentIndex == index) {
                    newSet.add(updatedElement);
                    updated = true;
                } else {
                    newSet.add(element);
                }
                currentIndex++;
            }

            if (index == currentIndex && !updated) {
                newSet.add(updatedElement);
            }

            collection = newSet;
        } else {
            throw new UnsupportedOperationException("Editing by index is not supported for this collection type");
        }
    }

    /**
     * Deletes the element at the specified index.
     *
     * @param index The index of the element to delete.
     */
    public void customDelete(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            if (index >= 0 && index < collection.size()) {
                ((List<T>) collection).remove(index);
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else if (type == CollectionType.TREE_SET) {
            TreeSet<T> newSet = new TreeSet<>();
            int currentIndex = 0;
            boolean removed = false;

            for (T element : collection) {
                if (currentIndex != index) {
                    newSet.add(element);
                } else {
                    removed = true;
                }
                currentIndex++;
            }

            if (!removed) {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }

            collection = newSet;
        } else {
            throw new UnsupportedOperationException("Deleting by index is not supported for this collection type");
        }
    }
}
