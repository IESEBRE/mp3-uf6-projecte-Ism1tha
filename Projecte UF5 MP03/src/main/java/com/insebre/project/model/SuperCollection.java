package com.insebre.project.model;

import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.ExistingElementTreeSetException;

import java.io.Serializable;
import java.util.*;

public class SuperCollection<T> implements List<T>, Set<T>, Serializable {

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
    public boolean addAll(int index, Collection<? extends T> c) {
        if (type == CollectionType.ARRAY_LIST) {
            return ((List<T>) collection).addAll(index, c);
        } else {
            throw new UnsupportedOperationException("addAll(int index, Collection<? extends T> c) is not supported for Set");
        }
    }

    public T get(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            if (index >= 0 && index < ((List<?>) collection).size()) {
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

    @Override
    public Spliterator<T> spliterator() {
        return collection.spliterator();
    }

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

            // Iterate over the existing elements to apply updates
            for (T element : collection) {
                if (currentIndex == index) {
                    // Replace the element at the specified index with the updated element
                    newSet.add(updatedElement);
                    updated = true;
                } else {
                    // Add the existing element to the new set
                    newSet.add(element);
                }
                currentIndex++;
            }

            // If the index is valid but out of bounds of the current size, add the updated element to the end
            if (index == currentIndex && !updated) {
                newSet.add(updatedElement);
            }

            // Update the collection to use the new TreeSet
            collection = newSet;
        } else {
            throw new UnsupportedOperationException("Editing by index is not supported for this collection type");
        }
    }

    public void customDelete(int index) {
        if (type == CollectionType.ARRAY_LIST) {
            if (index >= 0 && index < collection.size()) {
                ((List<T>) collection).remove(index);
            } else {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }
        } else if (type == CollectionType.TREE_SET) {
            // Create a new TreeSet to hold the updated elements
            TreeSet<T> newSet = new TreeSet<>();
            int currentIndex = 0;
            boolean removed = false;

            // Iterate over the existing elements to exclude the element at the specified index
            for (T element : collection) {
                if (currentIndex != index) {
                    // Add the existing element to the new set (excluding the element at the specified index)
                    newSet.add(element);
                } else {
                    removed = true;
                }
                currentIndex++;
            }

            // If the element at the specified index was not found, throw an exception
            if (!removed) {
                throw new IndexOutOfBoundsException("Index is out of bounds");
            }

            // Update the collection to use the new TreeSet without the deleted element
            collection = newSet;
        } else {
            throw new UnsupportedOperationException("Deleting by index is not supported for this collection type");
        }
    }
}
