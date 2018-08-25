package fusion.vdom

import org.w3c.dom.events.Event

fun t(value: String): VText = object : VText {
    override val value: String = value
}

fun e(
        type: String,
        attributes: Map<String, String>,
        listeners: Map<String, (Event) -> Unit>,
        vararg children: VNode
): VElement = object : VElement {
    override val type: String = type
    override val attributes: Map<String, String> = attributes
    override val listeners: Map<String, (Event) -> Unit> = listeners
    override val children: Array<out VNode> = children
}

fun e(
        type: String,
        attributes: Map<String, String>,
        vararg children: VNode
): VElement = object : VElement {
    override val type: String = type
    override val attributes: Map<String, String> = attributes
    override val listeners: Map<String, (Event) -> Unit> = emptyMap()
    override val children: Array<out VNode> = children
}

fun e(
        type: String,
        listeners: Map<String, (Event) -> Unit>,
        vararg children: VNode
): VElement = object : VElement {
    override val type: String = type
    override val attributes: Map<String, String> = emptyMap()
    override val listeners: Map<String, (Event) -> Unit> = listeners
    override val children: Array<out VNode> = children
}

fun e(
        type: String,
        vararg children: VNode
): VElement = object : VElement {
    override val type: String = type
    override val attributes: Map<String, String> = emptyMap()
    override val listeners: Map<String, (Event) -> Unit> = emptyMap()
    override val children: Array<out VNode> = children
}

fun f(vararg children: VNode): VFragment = object : VFragment {
    override val children: Array<out VNode> = children
}
