import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.HashSet

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
class MissingInteger {

    private val one = intArrayOf(1, 3, 6, 4, 1, 2)
    private val two = intArrayOf(1, 2, 3)
    private val three = intArrayOf(-1, -3)

    /**
     * O(n**2)
     * @param array
     * @return
     */
    private fun getResult(array: IntArray): Int {
        var result = 1

        for (i in 1..array.size) {
            if (array[i - 1] > 0) {
                if (contains(array, i)) {
                    result++
                } else {
                    result = i
                    break
                }
            }
        }

        return result
    }

    /**
     * O(n)
     * @param array Array to search in
     * @return Smallest positive integer
     */
    private fun getResultNew(array: IntArray): Int {
        val seen = HashSet<Int>()
        val min = 1

        for (anArray in array) {
            if (anArray > 0) {
                seen.add(anArray)
            }
        }

        for (i in 1 until Integer.MAX_VALUE) {
            if (!seen.contains(i)) {
                return i
            }
        }

        return min
    }

    private fun contains(array: IntArray, key: Int): Boolean {
        for (i in array) {
            if (i == key) {
                return true
            }
        }
        return false
    }

    @Test
    fun testOne() {
        assertEquals(5, getResult(one))
    }

    @Test
    fun testTwo() {
        assertEquals(4, getResult(two))
    }

    @Test
    fun testThree() {
        assertEquals(1, getResult(three))
    }

    @Test
    fun testOneNew() {
        assertEquals(5, getResultNew(one))
    }

    @Test
    fun testTwoNew() {
        assertEquals(4, getResultNew(two))
    }

    @Test
    fun testThreeNew() {
        assertEquals(1, getResultNew(three))
    }


}
