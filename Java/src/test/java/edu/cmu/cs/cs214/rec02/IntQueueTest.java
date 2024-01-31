package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
    //    mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        // mQueue.enqueue(1);
        // assertFalse(mQueue.isEmpty());
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertFalse(mQueue.isEmpty());
        }
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        // mQueue.enqueue(1);
        // assertEquals(1, mQueue.peek());
        // assertEquals(1, mQueue.size());
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        // enqueue
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        // dequeue in order
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(testList.get(i), mQueue.dequeue());
            assertEquals(2-i, mQueue.size());
        }
        // test for dequeue from an empty queue
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClear() {
        // enqueue
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testSize() {
        // test empty queue size
        assertEquals(0, mQueue.size());
        // enqueue
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(i+1, mQueue.size());
        }
        // dequeue
        for (int i = testList.size(); i > 0; i--) {
            mQueue.dequeue();
            assertEquals(i-1, mQueue.size());
        }
    }

    @Test
    public void testEnsureCapacityHeadIs0() {
        // Enqueue 10 elements to fill the queue
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }

        // Check size before the resize
        assertEquals(10, mQueue.size());

        // Enqueue one more element to trigger the resize
        mQueue.enqueue(10);

        // Check size after the resize
        assertEquals(11, mQueue.size());

        // Now dequeue all elements and check if they are in correct order
        for (int i = 0; i < 11; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Finally, check if the queue is empty
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testEnsureCapacityHeadNot0() {
        // Enqueue 10 elements to fill the queue
        for (int i = 0; i < 10; i++) { //[0,9]
            mQueue.enqueue(i);
        }

        // Check size before the resize
        assertEquals(10, mQueue.size());

        // Dequeue the head to change head to 1
        mQueue.dequeue(); //[1,9]
        mQueue.enqueue(10); //[1,10]
        assertEquals(10, mQueue.size());

        // Enqueue one more element to trigger the resize
        mQueue.enqueue(11); //[1,11]

        // Check size after the resize
        assertEquals(11, mQueue.size());

        // Now dequeue all elements and check if they are in correct order
        for (int i = 1; i < 12; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        // Finally, check if the queue is empty
        assertTrue(mQueue.isEmpty());
    }

}
