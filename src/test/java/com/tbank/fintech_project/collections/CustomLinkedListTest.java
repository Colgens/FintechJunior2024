package com.tbank.fintech_project.collections;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {


    @Test
    void testAddAndGet() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("someString");
        list.add("anotherString");
        assertEquals("someString", list.get(0));
        assertEquals("anotherString", list.get(1));
    }

    @Test
    void testRemove() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("someString");
        assertEquals(1, list.size());
        list.remove(0);
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void testContains() {
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("someString");
        assertTrue(list.contains("someString"));
        assertFalse(list.contains("anotherString"));
    }

    @Test
    void testAddAllFromStream() {
        CustomLinkedList<Integer> list = Stream.of(4, 8, 15, 16, 23, 42)
                .reduce(new CustomLinkedList<>(), (customList, element) -> {
                    customList.add(element);
                    return customList;
                }, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
        assertEquals(6, list.size());
        assertEquals("[4, 8, 15, 16, 23, 42]", list.toString());
    }

    @Test
    void testAddAllWithCollection() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        List<Integer> someList = List.of(4, 8, 15, 16, 23, 42);
        list.addAll(someList);
        assertEquals(6, list.size());
        assertEquals("[4, 8, 15, 16, 23, 42]", list.toString());
    }

    @Test
    void testAddAllWithCollectionByIndex() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        List<Integer> someList = List.of(4, 8, 15, 16, 23, 42);
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAll(1, someList));
        list.addAll(0, someList);
        list.addAll(1, List.of(0, 0, 0, 0));
        assertEquals("[4, 0, 0, 0, 0, 8, 15, 16, 23, 42]", list.toString());
    }

    @Test
    void testCollectionConstructor() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>(List.of(4, 8, 15, 16, 23, 42));
        assertEquals(6, list.size());
        assertEquals("[4, 8, 15, 16, 23, 42]", list.toString());
    }


    @Test
    void testClearList() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(5);
        list.add(6);
        list.add(7);
        assertEquals(3, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void testIsEmpty() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        assertTrue(list.isEmpty());
        list.add(5);
        assertFalse(list.isEmpty());
    }

    @Test
    void testAddAtIndex() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(0, 1);
        list.add(1, 2);
        list.add(0, 3);
        assertEquals(3, list.size());
        assertEquals(3, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    void testRemoveAtIndex() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void testRemoveFromEmptyList() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    void testAddAtIndexOutOfBounds() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));
    }

    @Test
    void testGetOutOfBounds() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void testAddAllAtIndex() {
        CustomLinkedList<Integer> list1 = new CustomLinkedList<>(List.of(4, 8, 15, 16, 23, 42));
        CustomLinkedList<Integer> list2 = new CustomLinkedList<>(List.of(0, 0, 0, 0));
        list1.addAll(2, list2);
        assertEquals("[4, 8, 0, 0, 0, 0, 15, 16, 23, 42]", list1.toString());
    }
}