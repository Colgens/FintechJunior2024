package com.tbank.fintech_project.collections;

import java.util.Collection;

public class CustomLinkedList<T> {
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    public CustomLinkedList() {
    }

    public CustomLinkedList(Collection<? extends T> collection) {
        this();
        addAll(collection);
    }

    private static class ListNode<T> {
        T data;
        ListNode<T> prev;
        ListNode<T> next;

        ListNode(ListNode<T> prev, T data, ListNode<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    public void add(T element) {
        ListNode<T> last = tail;
        ListNode<T> newListNode = new ListNode<>(last, element, null);
        tail = newListNode;
        if (last == null) {
            head = newListNode;
        } else {
            last.next = newListNode;
        }
        size++;
    }

    public void add(int index, T element) {
        checkAvailableIndex(index);

        if (index == size) {
            add(element);
        } else {
            addBefore(element, getNode(index));
        }
    }

    public T get(int index) {
        checkElementIndex(index);

        ListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public void remove(int index) {
        checkElementIndex(index);

        if (index == 0) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            ListNode<T> current = getNode(index);
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
    }

    public boolean contains(T element) {
        ListNode<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void addAll(Collection<? extends T> elements) {
        for (T element : elements) {
            add(element);
        }
    }

    public void addAll(CustomLinkedList<T> elements) {
        if (elements.isEmpty()) {
            return;
        }

        if (isEmpty()) {
            head = elements.head;
        } else {
            tail.next = elements.head;
            elements.head.prev = tail;
        }
        tail = elements.tail;
        size += elements.size();
    }


    public void addAll(int index, CustomLinkedList<T> elements) {
        addAllInternal(index, elements.head, elements.tail, elements.size());
    }

    public void addAll(int index, Collection<? extends T> elements) {
        if (elements.isEmpty()) {
            return;
        }

        ListNode<T> dummyHead = new ListNode<>(null, null, null);
        ListNode<T> current = dummyHead;

        for (T element : elements) {
            ListNode<T> newNode = new ListNode<>(current, element, null);
            current.next = newNode;
            current = newNode;
        }

        addAllInternal(index, dummyHead.next, current, elements.size());
    }

    private void addAllInternal(int index, ListNode<T> newHead, ListNode<T> newTail, int newSize) {
        checkAvailableIndex(index);

        if (newSize == 0) {
            return;
        }

        ListNode<T> current = (index == size) ? null : getNode(index);
        ListNode<T> previous = (current == null) ? tail : current.prev;

        if (previous == null) {
            head = newHead;
        } else {
            previous.next = newHead;
            newHead.prev = previous;
        }

        if (current == null) {
            tail = newTail;
        } else {
            newTail.next = current;
            current.prev = newTail;
        }

        size += newSize;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkAvailableIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Введенный индекс выходит за пределы");
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Введенный индекс выходит за пределы");
        }
    }

    private ListNode<T> getNode(int index) {
        ListNode<T> current;
        if (size - index > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void addBefore(T element, ListNode<T> targetNode) {
        ListNode<T> previous = targetNode.prev;
        ListNode<T> newNode = new ListNode<>(previous, element, targetNode);
        targetNode.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}