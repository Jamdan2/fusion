package fusion.vdom

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.get

fun diffVNodes(rootNode: Node, lastVNode: VNode?, nextVNode: VNode?, index: Int = 0): Int {
    try {
        when {
            lastVNode is VFragment && nextVNode !is VFragment -> {
                var delta = 0
                lastVNode.children.forEachIndexed { i, vNode ->
                    if (i == 0) diffVNodes(rootNode, vNode, nextVNode, i)
                    else delta += diffVNodes(rootNode, vNode, null, i)
                }
                return delta
            }
            lastVNode !is VFragment && nextVNode is VFragment -> {
                var delta = 0
                nextVNode.children.forEachIndexed { i, vNode ->
                    if (i == 0) diffVNodes(rootNode, vNode, lastVNode, i)
                    else delta += diffVNodes(rootNode, null, vNode, i)
                }
                return delta
            }
            lastVNode == null -> {
                if (nextVNode != null) {
                    rootNode.appendChild(processVNodeExpandFragments(nextVNode))
                    return 1
                } else {
                    throw IllegalArgumentException("Arguments lastVNode and nextVNode cannot both be null.")
                }
            }
            nextVNode == null -> {
                rootNode.removeChild(rootNode.childNodes[index]!!)
                return -1
            }
            !compareVNodes(lastVNode, nextVNode) -> {
                rootNode.replaceChild(
                        processVNodeExpandFragments(nextVNode),
                        rootNode.childNodes[index]!!
                )
                return 0
            }
            (lastVNode is VElement && nextVNode is VElement) -> {
                return diffVElements(rootNode, lastVNode, nextVNode, index)
            }
            (lastVNode is VFragment && nextVNode is VFragment) -> {
                return diffVFragments(rootNode, lastVNode, nextVNode)
            }
            else -> {
                return 0
            }
        }
    } catch (e: NullPointerException) {
        throw IllegalArgumentException("Argument index must point to an existing index of rootNode.childNodes.")
    }
}

fun diffVElements(rootNode: Node, lastVNode: VElement, nextVNode: VElement, index: Int): Int {
    val lastChildren = lastVNode.childrenExpandFragments
    val nextChildren = nextVNode.childrenExpandFragments
    val lastSize = lastChildren.size
    val nextSize = nextChildren.size
    var i = 0
    var delta = 0
    while (i < lastSize || i < nextSize) {
        delta += diffVNodes(
                rootNode.childNodes[index]!!,
                try {
                    lastChildren[i]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                try {
                    nextChildren[i]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                i + delta
        )
        i++
    }
    with(rootNode.childNodes[index]!!) {
        if (this is Element) {
            (lastVNode.attributes + nextVNode.attributes).filter {
                nextVNode.attributes[it.key] != null
            }.forEach {
                setAttribute(it.key, it.value)
            }
            (lastVNode.listeners + nextVNode.listeners).filter {
                nextVNode.attributes[it.key] != null
            }.forEach {
                addEventListener(it.key, it.value)
            }
        }
    }
    return delta
}

fun diffVFragments(rootNode: Node, lastVNode: VFragment, nextVNode: VFragment): Int {
    val lastChildren = lastVNode.childrenExpandFragments
    val nextChildren = nextVNode.childrenExpandFragments
    val lastSize = lastChildren.size
    val nextSize = nextChildren.size
    var i = 0
    var delta = 0
    while (i < lastSize || i < nextSize) {
        delta += diffVNodes(
                rootNode,
                try {
                    lastChildren[i]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                try {
                    nextChildren[i]
                } catch (e: IndexOutOfBoundsException) {
                    null
                },
                i + delta
        )
        i++
    }
    return delta
}
