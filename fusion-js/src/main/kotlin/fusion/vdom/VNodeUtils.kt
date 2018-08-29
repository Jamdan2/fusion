package fusion.vdom

fun Array<out VNode>.forEachExpandFragments(action: (VNode) -> Unit) {
    forEach { vNode ->
        if (vNode is VFragment) vNode.children.forEachExpandFragments { action(it) }
        else action(vNode)
    }
}

fun <T> Array<out VNode>.mapExpandFragments(action: (VNode) -> T): Array<out T> {
    val result = arrayListOf<T>()
    forEachExpandFragments {
        result += action(it)
    }
    return result.toTypedArray()
}

val VElement.childrenExpandFragments: Array<out VNode> get() {
    return children.mapExpandFragments { it }
}

val VFragment.childrenExpandFragments: Array<out VNode> get() {
    return children.mapExpandFragments { it }
}

fun compareVNodes(lastVNode: VNode, nextVNode: VNode): Boolean {
    return (
            (lastVNode as? VText)?.value == (nextVNode as? VText)?.value
            && (lastVNode as? VElement)?.type == (nextVNode as? VElement)?.type
    )
}
