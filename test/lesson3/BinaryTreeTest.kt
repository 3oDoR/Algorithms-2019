package lesson3

import org.junit.jupiter.api.Tag
import java.util.*
import kotlin.test.*

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        assertEquals(0, tree.size)
        assertFalse(tree.contains(5))
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val binarySet = create()
            assertFalse(binarySet.remove(42))
            for (element in list) {
                binarySet += element
            }
            val originalHeight = binarySet.height()
            val toRemove = list[random.nextInt(list.size)]
            val oldSize = binarySet.size
            assertTrue(binarySet.remove(toRemove))
            assertEquals(oldSize - 1, binarySet.size)
            println("Removing $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
            assertTrue(
                binarySet.height() <= originalHeight,
                "After removal of $toRemove from $list binary tree height increased"
            )
        }

        //My tests
        //usual case
        val usualTestList = mutableListOf<Int>(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27)
        val usualBinarySet = create();
        for (element in usualTestList) {
            usualBinarySet += element
        }
        val usualOriginalHeight = usualBinarySet.height()
        val usualToRemove = 5;
        val usualOldSize = usualBinarySet.size
        assertEquals(usualTestList.contains(5), true)
        assertTrue(usualBinarySet.remove(usualToRemove))
        assertEquals(usualOldSize - 1, usualBinarySet.size)
        assertNotEquals(usualOldSize, usualBinarySet.size)
        println("(My tests 1(Usual case)) Removing 5 from $usualBinarySet")
        for (element in usualTestList) {
            val inn = element != 5
            assertEquals(
                inn, element in usualBinarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(usualBinarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
        assertTrue(
            usualBinarySet.height() <= usualOriginalHeight,
            "After removal of 499 from $usualTestList binary tree height increased"
        )

        //My tests
        //2 Long test
        val longTestList = mutableListOf<Int>(
            1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53,
            55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99, 101, 103,
            105, 107, 109, 111, 113, 115, 117, 119, 121, 123, 125, 127, 129, 131, 133, 135, 137, 139, 141, 143, 145,
            147, 149, 151, 153, 155, 157, 159, 161, 163, 165, 167, 169, 171, 173, 175, 177, 179, 181, 183, 185, 187,
            189, 191, 193, 195, 197, 199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219, 221, 223, 225, 227, 229,
            231, 233, 235, 237, 239, 241, 243, 245, 247, 249, 251, 253, 255, 257, 259, 261, 263, 265, 267, 269, 271,
            273, 275, 277, 279, 281, 283, 285, 287, 289, 291, 293, 295, 297, 299, 301, 303, 305, 307, 309, 311, 313,
            315, 317, 319, 321, 323, 325, 327, 329, 331, 333, 335, 337, 339, 341, 343, 345, 347, 349, 351, 353, 355,
            357, 359, 361, 363, 365, 367, 369, 371, 373, 375, 377, 379, 381, 383, 385, 387, 389, 391, 393, 395, 397,
            399, 401, 403, 405, 407, 409, 411, 413, 415, 417, 419, 421, 423, 425, 427, 429, 431, 433, 435, 437, 439,
            441, 443, 445, 447, 449, 451, 453, 455, 457, 459, 461, 463, 465, 467, 469, 471, 473, 475, 477, 479, 481,
            483, 485, 487, 489, 491, 493, 495, 497, 499, 501, 503, 505, 507, 509, 511, 513, 515, 517, 519, 521, 523,
            525, 527, 529, 531, 533, 535, 537, 539, 541, 543, 545, 547, 549, 551, 553, 555, 557, 559, 561, 563, 565,
            567, 569, 571, 573, 575, 577, 579, 581, 583, 585, 587, 589, 591, 593, 595, 597, 599, 601, 603, 605, 607,
            609, 611, 613, 615, 617, 619, 621, 623, 625, 627, 629, 631, 633, 635, 637, 639, 641, 643, 645, 647, 649,
            651, 653, 655, 657, 659, 661, 663, 665, 667, 669, 671, 673, 675, 677, 679, 681, 683, 685, 687, 689, 691,
            693, 695, 697, 699, 701, 703, 705, 707, 709, 711, 713, 715, 717, 719, 721, 723, 725, 727, 729, 731, 733,
            735, 737, 739, 741, 743, 745, 747, 749, 751, 753, 755, 757, 759, 761, 763, 765, 767, 769, 771, 773, 775,
            777, 779, 781, 783, 785, 787, 789, 791, 793, 795, 797, 799, 801, 803, 805, 807, 809, 811, 813, 815, 817,
            819, 821, 823, 825, 827, 829, 831, 833, 835, 837, 839, 841, 843, 845, 847, 849, 851, 853, 855, 857, 859,
            861, 863, 865, 867, 869, 871, 873, 875, 877, 879, 881, 883, 885, 887, 889, 891, 893, 895, 897, 899, 901,
            903, 905, 907, 909, 911, 913, 915, 917, 919, 921, 923, 925, 927, 929, 931, 933, 935, 937, 939, 941, 943,
            945, 947, 949, 951, 953, 955, 957, 959, 961, 963, 965, 967, 969, 971, 973, 975, 977, 979, 981, 983, 985,
            987, 989, 991, 993, 995, 997, 999
        );
        val longBinarySet = create();
        for (element in longTestList) {
            longBinarySet += element
        }
        val longOriginalHeight = longBinarySet.height()
        val longToRemove = 499;
        val longOldSize = longBinarySet.size
        assertEquals(longTestList.contains(499), true)
        assertTrue(longBinarySet.remove(longToRemove))
        assertEquals(longOldSize - 1, longBinarySet.size)
        assertNotEquals(longOldSize, longBinarySet.size)
        println("(My tests 2(Long test)) Removing 499 from $longBinarySet")
        for (element in longTestList) {
            val inn = element != 499
            assertEquals(
                inn, element in longBinarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(longBinarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
        assertTrue(
            longBinarySet.height() <= longOriginalHeight,
            "After removal of 499 from $longTestList binary tree height increased"
        )

        //My tests
        //3 Edge case
        val edgeCasesTestList = mutableListOf<Int>(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27)
        val edgeCasesBinarySet = create();
        for (element in edgeCasesTestList) {
            edgeCasesBinarySet += element
        }
        val edgeCasesOriginalHeight = edgeCasesBinarySet.height()
        val edgeCasesToRemove = 2;
        val edgeCasesOldSize = edgeCasesBinarySet.size
        assertEquals(edgeCasesTestList.contains(2), false)
        assertFalse(longBinarySet.remove(edgeCasesToRemove))
        assertEquals(edgeCasesOldSize,edgeCasesBinarySet.size)

        println("(My tests 3(Edge case)) Removing 2 from $edgeCasesBinarySet")
        for (element in edgeCasesTestList) {
            val inn = element != 2
            assertEquals(
                inn, element in edgeCasesBinarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(edgeCasesBinarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
        assertTrue(
            edgeCasesBinarySet.height() <= edgeCasesOriginalHeight,
            "After removal of 2 from $edgeCasesTestList binary tree height increased"
        )
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
            //My test
            val myList = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
            val myTreeSet = TreeSet<Int>()
            val myBinarySet = create()
            assertFalse(myBinarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in myList) {
                myTreeSet += element
                myBinarySet += element
            }
            val myTreeIt = myTreeSet.iterator()
            val myBinaryIt = myBinarySet.iterator()
            println("Traversing $myList")
            while (myTreeIt.hasNext()) {
                assertEquals(myTreeIt.next(), myBinaryIt.next(), "Incorrect iterator state while iterating $myTreeSet")
            }
            val MyIterator1 = myBinarySet.iterator()
            val MyIterator2 = myBinarySet.iterator()
            println("Consistency check for hasNext $myList")
            // hasNext call should not affect iterator position
            while (MyIterator1.hasNext()) {
                assertEquals(
                    MyIterator2.next(), MyIterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $myTreeSet"
                )
            }
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            assertEquals(
                0, counter,
                "Iterator.remove() of $toRemove from $list changed iterator position: " +
                        "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
            )
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}