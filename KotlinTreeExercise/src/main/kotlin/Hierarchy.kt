/**
 * A `Hierarchy` stores an arbitrary _forest_ (an ordered collection of ordered trees)
 * as an array of node IDs in the order of DFS traversal, combined with a parallel array of node depths.
 *
 * Parent-child relationships are identified by the position in the array and the associated depth.
 * Each tree root has depth 0, its children have depth 1 and follow it in the array, their children have depth 2 and follow them, etc.
 *
 * Example:
 * ```
 * nodeIds: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
 * depths:  0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2
 * ```
 *
 * the forest can be visualized as follows:
 * ```
 * 1
 * - 2
 * - - 3
 * - - - 4
 * - 5
 * 6
 * - 7
 * 8
 * - 9
 * - 10
 * - - 11
 *```
 * 1 is a parent of 2 and 5, 2 is a parent of 3, etc. Note that depth is equal to the number of hyphens for each node.
 *
 * Invariants on the depths array:
 *  * Depth of the first element is 0.
 *  * If the depth of a node is `D`, the depth of the next node in the array can be:
 *      * `D + 1` if the next node is a child of this node;
 *      * `D` if the next node is a sibling of this node;
 *      * `d < D` - in this case the next node is not related to this node.
 */
interface Hierarchy {
    /** The number of nodes in the hierarchy. */
    val size: Int

    /**
     * Returns the unique ID of the node identified by the hierarchy index. The depth for this node will be `depth(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun nodeId(index: Int): Int

    /**
     * Returns the depth of the node identified by the hierarchy index. The unique ID for this node will be `nodeId(index)`.
     * @param index must be non-negative and less than [size]
     * */
    fun depth(index: Int): Int

    fun formatString(): String {
        return (0 until size).joinToString(
            separator = ", ",
            prefix = "[",
            postfix = "]"
        ) { i -> "${nodeId(i)}:${depth(i)}" }
    }

    /**
     * Returns the parent ID of the node identified by the hierarchy index.
     * For the root of the forest, returns -1.
     * @param index must be non-negative and less than [size]
     */
    fun parentId(i: Int): Int {
        if (i == 0) {
            return -1
        }

        val currentDepth = depth(i)
        for (j in i - 1 downTo 0) {
            if (depth(j) == currentDepth - 1) {
                return j
            }
        }

        return -1
    }
}

/**
 * A node is present in the filtered hierarchy iff its node ID passes the predicate and all of its ancestors pass it as well.
 */
fun Hierarchy.filter(nodeIdPredicate: (Int) -> Boolean): Hierarchy {
    val meetsFilter = BooleanArray(size)

    val retNodeIds = ArrayList<Int>()
    val retDepths = ArrayList<Int>()

    // Keep track of the last time we saw each depth, mapping depth to the index of the node.
    val depthMap = HashMap<Int, Int>()

    // We use -1 as a sentinel value to indicate that there is no parent for the root of each tree.
    depthMap[-1] = -1

    for (i in 0 until size) {
        depthMap[depth(i)] = i

        val parentIdVal = depthMap[depth(i) - 1]
            ?: throw IllegalArgumentException("Parent ID not found for node at depth ${depth(i) - 1}")

        // If the parentIdVal is -1, then we're looking at a root node of a tree, where the forest is
        // the parent, so we don't need to check if the parent meets the filter.
        val checkParent = ( parentIdVal != -1 && meetsFilter[parentIdVal] ) || ( parentIdVal == -1 )

        if (nodeIdPredicate.invoke(nodeId(i)) && checkParent) {
            retNodeIds.add(nodeId(i))
            retDepths.add(depth(i))
            meetsFilter[i] = true
        }
    }

    return ArrayBasedHierarchy(retNodeIds.toIntArray(), retDepths.toIntArray())
}

class ArrayBasedHierarchy(
    private val myNodeIds: IntArray,
    private val myDepths: IntArray,
) : Hierarchy {
    override val size: Int = myDepths.size

    override fun nodeId(index: Int): Int = myNodeIds[index]

    override fun depth(index: Int): Int = myDepths[index]
}
