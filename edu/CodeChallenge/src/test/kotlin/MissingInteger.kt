import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Write a function:
 *
 * class Solution { public int solution(int[] A); }
 *
 * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
 *
 * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
 *
 * Given A = [1, 2, 3], the function should return 4.
 *
 * Given A = [−1, −3], the function should return 1.
 *
 * Write an efficient algorithm for the following assumptions:
 *
 * N is an integer within the range [1..100,000];
 * each element of array A is an integer within the range [−1,000,000..1,000,000].
 */
public class MissingInteger {

    private int[] one = {1,3,6,4,1,2};
    private int[] two = {1,2,3};
    private int[] three = {-1,-3};

    /**
     * O(n**2)
     * @param array
     * @return
     */
    private int getResult(int[] array) {
        int result = 1;

        for (int i = 1; i <= array.length; i++) {
            if (array[i-1] > 0) {
                if (contains(array, i)) {
                    result++;
                } else {
                    result = i;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * O(n)
     * @param array Array to search in
     * @return Smallest positive integer
     */
    private int getResultNew(int[] array) {
        HashSet<Integer> seen = new HashSet<>();
        int min = 1;

        for (int anArray : array) {
            if (anArray > 0) {
                seen.add(anArray);
            }
        }

        for(int i = 1 ; i < Integer.MAX_VALUE; i++) {
            if(!seen.contains(i)) {
                return i;
            }
        }

        return min;
    }

    private boolean contains(final int[] array, final int key) {
        for (final int i : array) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testOne() {
        Assert.assertEquals(5, getResult(one));
    }

    @Test
    public void testTwo() {
        Assert.assertEquals(4, getResult(two));
    }

    @Test
    public void testThree() {
        Assert.assertEquals(1, getResult(three));
    }

    @Test
    public void testOneNew() {
        Assert.assertEquals(5, getResultNew(one));
    }

    @Test
    public void testTwoNew() {
        Assert.assertEquals(4, getResultNew(two));
    }

    @Test
    public void testThreeNew() {
        Assert.assertEquals(1, getResultNew(three));
    }


}
