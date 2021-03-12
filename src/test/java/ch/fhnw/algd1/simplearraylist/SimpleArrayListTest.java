package ch.fhnw.algd1.simplearraylist;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.fhnw.algd1.simplearraylist.SimpleArrayList;

public class SimpleArrayListTest {
	private SimpleArrayList<Integer> list;

	@Before
	public void setUp() {
		list = new SimpleArrayList<>();
	}

	@Test
	public void testGetOnValidIndex() {
		list.add(1);
		list.add(2);
		assertEquals(1, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(2, list.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOnNegativIndex_ExpectException() {
		list.add(1);
		list.get(-4);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOnTooLargeIndex_ExpectException() {
		list.add(1);
		list.get(4);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetOnSizeAsIndex_ExpectException() {
		list.add(1);
		list.add(2);
		list.add(3);
		list.get(list.size());
		fail("Exception was expected");
	}

	@Test
	public void testIndexAdd_WithoutEnlarge() {
		int[] numbers = new int[] { 1, 4, 6, 7 };
		for (int i = numbers.length - 1; i >= 0; i--) {
			list.add(0, numbers[i]);
		}
		assertListValues(numbers);
	}

	@Test
	public void testIndexSet() {
		int[] numbers = new int[] { 1, 4, 6, 7 };
		int last = numbers[numbers.length - 1];
		list.add(numbers[numbers.length - 1]);
		for (int i = numbers.length - 2; i >= 0; i--) {
			assertEquals(last, list.set(0, numbers[i]).intValue());
			last = numbers[i];
		}
		assertEquals(numbers[0], list.get(0).intValue());
		assertEquals(1, list.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetOnNegativIndex_ExpectException() {
		list.add(1);
		list.set(-4, 2);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetOnTooLargeIndex_ExpectException() {
		list.add(1);
		list.set(4, 2);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddOnNegativIndex_ExpectException() {
		list.add(-1, 2);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddOnHugeTooLargeIndex_ExpectException() {
		list.add(0, 1);
		list.add(44444, 2);
		fail("Exception was expected");
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddOnSmallTooLargeIndex_ExpectException() {
		list.add(0, 1);
		list.add(2, 2);
		fail("Exception was expected");
	}

	@Test
	public void testAddWithEnlarge() {
		int[] numbers = new int[1000];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i;
			list.add(i, i);
		}
		assertListValues(numbers);
	}

	@Test
	public void testAddWithEnlargeReverse() {
		int[] numbers = new int[1000];
		for (int i = numbers.length - 1; i >= 0; i--) {
			numbers[i] = i;
			list.add(0, i);
		}
		assertListValues(numbers);
	}

	@Test
	public void testAddAnywhere() {
		int[] numbers = { 10, 21, 11, 22, 12, 23, 13, 24, 14, 25, 15, 26, 16, 27, 17, 28, 0, 29, 1, 30, 2, 31, 3, 32, 4, 33,
				5, 34, 6, 35, 7, 36 };
		for (int i = 0; i < 8; i++)
			list.add(i);
		for (int i = 0; i < 8; i++)
			list.add(i, 10 + i);
		for (int i = 16; i > 0; i--)
			list.add(i, 20 + i);
		assertListValues(numbers);
	}

	@Test
	public void testRemove() {
		int[] numbers = new int[10];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i;
			list.add(i, i);
		}
		assertListValues(numbers);
		list.remove(1);
		list.remove(0);
		int[] remainingNumbers = Arrays.copyOfRange(numbers, 2, numbers.length);
		assertListValues(remainingNumbers);
	}

	@Test
	public void addNullValue() {
		list.add(null);
	}

	@Test
	public void testEnlargeAfterPotentialShrink() {
		final int N = 5000, N2 = 4 * N / 5;
		for (int i = 0; i < N; i++)
			list.add(i);
		for (int i = 0; i < N2; i++)
			list.remove(0);
		for (int i = 0; i < N2; i++)
			list.add(i);
		int[] numbers = new int[N];
		for (int i = N2; i < N; i++)
			numbers[i - N2] = i;
		for (int i = 0; i < N2; i++)
			numbers[i + N - N2] = i;
		assertListValues(numbers);
	}

	private void assertListValues(int[] numbers) {
		assertEquals(numbers.length, list.size());
		for (int i = 0; i < numbers.length; i++) {
			assertEquals(numbers[i], list.get(i).intValue());
		}
	}
}
