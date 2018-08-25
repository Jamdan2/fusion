package fusion.vdom

import org.w3c.dom.events.Event
import kotlin.browser.document

interface VNode

interface VText : VNode {
    val value: String
}

interface VElement : VNode {
    val type: String
    val attributes: Map<String, String>
    val listeners: Map<String, (Event) -> Unit>
    val children: Array<out VNode>
}

interface VFragment : VNode {
    val children: Array<out VNode>
}
