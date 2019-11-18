package lesson3

import java.util.*
import kotlin.test.*

abstract class AbstractHeadTailTest {
    private lateinit var tree: SortedSet<Int>
    private lateinit var randomTree: SortedSet<Int>
    private val randomTreeSize = 1000
    private val randomValues = mutableListOf<Int>()
    private lateinit var myTreeTest: SortedSet<Int>

    protected fun fillTree(create: () -> SortedSet<Int>) {
        this.tree = create()
        this.myTreeTest = create()
        val myListTest = mutableListOf(45, 1, 22, 35, 87, 66, 21, 88, 32, 146, 1567, 124, 6342, 632, 243)
        for (element in myListTest) {
            myTreeTest.add(element)
        }
        //




        //В произвольном порядке добавим числа от 1 до 10
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)

        this.randomTree = create()
        val random = Random()
        for (i in 0 until randomTreeSize) {
            val randomValue = random.nextInt(randomTreeSize) + 1
            if (randomTree.add(randomValue)) {
                randomValues.add(randomValue)
            }
        }
    }


    protected fun doHeadSetTest() {
        var set: SortedSet<Int> = tree.headSet(5)
        val mySetTest: SortedSet<Int> = myTreeTest.headSet(66)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))

        //My test
        assertEquals(false, mySetTest.contains(87))
        assertEquals(false, mySetTest.contains(124))
        assertEquals(false, mySetTest.contains(146))
        assertEquals(false, mySetTest.contains(1567))
        assertEquals(false, mySetTest.contains(124))
        assertEquals(true, mySetTest.contains(1))
        assertEquals(true, mySetTest.contains(22))
        assertEquals(true, mySetTest.contains(45))




        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

    }

    protected fun doTailSetTest() {
        var set: SortedSet<Int> = tree.tailSet(5)
        val mySetTest: SortedSet<Int> = myTreeTest.tailSet(66)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))

        //My test
        assertEquals(true, mySetTest.contains(87))
        assertEquals(true, mySetTest.contains(124))
        assertEquals(true, mySetTest.contains(146))
        assertEquals(true, mySetTest.contains(1567))
        assertEquals(true, mySetTest.contains(124))
        assertEquals(false, mySetTest.contains(1))
        assertEquals(false, mySetTest.contains(22))
        assertEquals(false, mySetTest.contains(45))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

    }

    protected fun doHeadSetRelationTest() {
        val set: SortedSet<Int> = tree.headSet(7)
        val mySetTest: SortedSet<Int> = myTreeTest.headSet(66)
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.add(-2)
        assertTrue(tree.contains(-2))
        tree.add(12)
        assertFalse(set.contains(12))
        assertFailsWith<IllegalArgumentException> { set.add(8) }
        assertEquals(8, set.size)
        assertEquals(13, tree.size)

        //My test
        assertEquals(6, mySetTest.size)
        assertEquals(15, myTreeTest.size)
        myTreeTest.add(2)
        assertTrue(mySetTest.contains(2))
        mySetTest.add(-3)
        assertTrue(myTreeTest.contains(-3))
        myTreeTest.add(68)
        assertFalse(set.contains(68))
        assertFailsWith<IllegalArgumentException> { mySetTest.add(68) }
        assertEquals(8, mySetTest.size)
        assertEquals(18, myTreeTest.size)
    }

    protected fun doTailSetRelationTest() {
        val set: SortedSet<Int> = tree.tailSet(4)
        val mySetTest: SortedSet<Int> = myTreeTest.tailSet(66)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.add(42)
        assertTrue(tree.contains(42))
        tree.add(0)
        assertFalse(set.contains(0))
        assertFailsWith<IllegalArgumentException> { set.add(-2) }
        assertEquals(9, set.size)
        assertEquals(13, tree.size)

        //My test
        assertEquals(9, mySetTest.size)
        assertEquals(15, myTreeTest.size)
        myTreeTest.add(67)
        assertTrue(mySetTest.contains(67))
        mySetTest.add(68)
        assertTrue(myTreeTest.contains(68))
        myTreeTest.add(2)
        assertFalse(mySetTest.contains(2))
        assertFailsWith<IllegalArgumentException> { mySetTest.add(-2) }
        assertEquals(11, mySetTest.size)
        assertEquals(18, myTreeTest.size)
    }

    protected fun doSubSetTest() {
        val smallSet: SortedSet<Int> = tree.subSet(3, 8)
        val mySmallSetTest: SortedSet<Int> = myTreeTest.subSet(22,88)
        assertEquals(false, smallSet.contains(1))
        assertEquals(false, smallSet.contains(2))
        assertEquals(true, smallSet.contains(3))
        assertEquals(true, smallSet.contains(4))
        assertEquals(true, smallSet.contains(5))
        assertEquals(true, smallSet.contains(6))
        assertEquals(true, smallSet.contains(7))
        assertEquals(false, smallSet.contains(8))
        assertEquals(false, smallSet.contains(9))
        assertEquals(false, smallSet.contains(10))

        assertEquals(false, mySmallSetTest.contains(1))
        assertEquals(false, mySmallSetTest.contains(146))
        assertEquals(false, mySmallSetTest.contains(3))
        assertEquals(true, mySmallSetTest.contains(22))
        assertEquals(false, mySmallSetTest.contains(88))
        assertEquals(true, mySmallSetTest.contains(66))
        assertEquals(false, mySmallSetTest.contains(28))
        assertEquals(false, mySmallSetTest.contains(1567))
        assertEquals(false, mySmallSetTest.contains(6342))
        assertEquals(false, mySmallSetTest.contains(632))



        assertFailsWith<IllegalArgumentException> { smallSet.add(2) }
        assertFailsWith<IllegalArgumentException> { smallSet.add(9) }



        val allSet = tree.subSet(-128, 128)
        for (i in 1..10)
            assertEquals(true, allSet.contains(i))

        val random = Random()
        val toElement = random.nextInt(randomTreeSize) + 1
        val fromElement = random.nextInt(toElement - 1) + 1

        val randomSubset = randomTree.subSet(fromElement, toElement)
        randomValues.forEach { element ->
            assertEquals(element in fromElement until toElement, randomSubset.contains(element))
        }
    }

    protected fun doSubSetRelationTest() {
        val set: SortedSet<Int> = tree.subSet(2, 15)
        assertEquals(9, set.size)
        assertEquals(10, tree.size)
        tree.add(11)
        assertTrue(set.contains(11))
        set.add(14)
        assertTrue(tree.contains(14))
        tree.add(0)
        assertFalse(set.contains(0))
        tree.add(15)
        assertFalse(set.contains(15))
        assertFailsWith<IllegalArgumentException> { set.add(1) }
        assertFailsWith<IllegalArgumentException> { set.add(20) }
        assertEquals(11, set.size)
        assertFalse(tree.remove(43))
        assertEquals(11, set.size)
        assertEquals(14, tree.size)
    }

}