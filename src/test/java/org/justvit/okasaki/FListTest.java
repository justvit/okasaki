package org.justvit.okasaki;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for simple functional list
 */
public class FListTest {

    @Test
    public void testHead() throws Exception {
        FList<String> list = new FList<>("a", "b", "c", "d", "e");
        assertEquals(list.head(), "a");
    }

    @Test
    public void testHead1() throws Exception {
        FList<String> list = new FList<>("abc");
        assertEquals(list.head(), "abc");
    }

    @Test
    public void testHead0() throws Exception {
        FList<String> list = new FList<>();
        try {
            list.head();
            fail("No exception thrown by head()");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("head()"));
        }
    }

    @Test
    public void testTail() throws Exception {
        FList<String> list = new FList<>("a", "b", "c", "d", "e");
        FList<String> tailList = new FList<>("b", "c", "d", "e");
        assertEquals(list.tail(), tailList);
    }

    @Test
    public void testTail1() throws Exception {
        FList<String> list = new FList<>("abc");
        FList<String> tailList = new FList<>();
        assertEquals(list.tail(), FList.EMPTY);
    }

    @Test
    public void testTail0() throws Exception {
        FList<String> list = new FList<>();
        try {
            list.tail();
            fail("No exception thrown by tail()");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("tail()"));
        }
    }

    @Test
    public void testLength() throws Exception {
        FList<String> list = new FList<>("a", "b", "c", "d", "e");
        assertEquals(list.length(), 5);
    }

    @Test
    public void testLength1() throws Exception {
        FList<String> list = new FList<>("abc");
        assertEquals(list.length(), 1);
    }

    @Test
    public void testLength0() throws Exception {
        FList<String> list = new FList<>();
        assertEquals(list.length(), 0);
    }

    @Test
    public void testFilter() throws Exception {
        FList<Integer> list = new FList<>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        FList<Integer> even = new FList<>(2, 4, 6, 8, 10);

        IFList<Integer> filtered = list.filter(i -> i % 2 == 0);
        assertEquals(filtered, even);
    }

    @Test
    public void testMap() throws Exception {
        FList<String> list = new FList<>("1", "2", "3", "4", "5");
        FList<Integer> numbers = new FList<>(1, 2, 3, 4, 5);

        FList<Integer> mapped = list.map(s -> Integer.parseInt(s));
        assertEquals(mapped, numbers);
    }

    @Test
    public void testFlatMap() throws Exception {
        FList<String> list = new FList<>("one for the money", "two for the show",
                "three to get ready", "now", "go", "cat", "go");
        FList<String> words = new FList<>("one", "for", "the", "money", "two", "for", "the", "show",
                "three", "to", "get", "ready", "now", "go", "cat", "go");

        IFList<String> splitted = list.flatMap((String s) -> new FList<String>(s.split(" ")));
        assertEquals(splitted, words);

    }

    @Test
    public void testFoldl() throws Exception {
        FList<String> list = new FList<>("1", "2", "3", "4", "5");
        String foldled = list.foldl("#", (String s, String acc) -> acc.concat(s));
        assertEquals(foldled, "#12345");
    }

    @Test
    public void testFoldr() throws Exception {

    }

    @Test
    public void testClone() throws Exception {

    }
}