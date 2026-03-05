import kotlin.test.*

class FilterTest {
    @Test
    fun testFilterProvidedTest() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            intArrayOf(0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 5, 8, 10, 11),
            intArrayOf(0, 1, 1, 0, 1, 2))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterEmpty() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(),
            intArrayOf())
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(),
            intArrayOf())
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterOne() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1),
            intArrayOf(0))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1),
            intArrayOf(0))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterOneTreeTwoElements() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2),
            intArrayOf(0, 1))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2),
            intArrayOf(0, 1))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterTwoTreesTwoElements() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2),
            intArrayOf(0, 0))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2),
            intArrayOf(0, 0))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterHigherNodeIdsWithOneSidedTree() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 5 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(0, 1, 2, 3))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterHigherNodeIdsWithAllSiblings() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
            intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 5 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 6, 7, 8, 9, 11),
            intArrayOf(0, 1, 1, 1, 1, 1, 1, 1, 1))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

    @Test
    fun testFilterHigherNodeIdsWithTwoTrees() {
        val unfiltered: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8),
            intArrayOf(0, 1, 2, 3, 1, 2, 3, 4))
        val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 5 != 0 }
        val filteredExpected: Hierarchy = ArrayBasedHierarchy(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(0, 1, 2, 3))
        assertEquals(filteredExpected.formatString(), filteredActual.formatString())
    }

}
