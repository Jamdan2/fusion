package fusion.vdom

import org.w3c.dom.DocumentFragment
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.Text
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import kotlin.browser.document

fun processVNode(vNode: VNode): Node = when (vNode) {
    is VText -> processVText(vNode)
    is VElement -> processVElement(vNode)
    is VFragment -> processVFragment(vNode)
    else -> throw IllegalArgumentException("Argument vNode must be of type VFragment, VElement or VText")
}

fun processVNodeExpandFragments(vNode: VNode): Node = when (vNode) {
    is VText -> processVText(vNode)
    is VElement -> processVElementExpandFragments(vNode)
    is VFragment -> processVFragmentExpandFragments(vNode)
    else -> throw IllegalArgumentException("Argument vNode must be of type VFragment, VElement or VText")
}

fun processVText(vText: VText): Text = document.createTextNode(vText.value)

fun processVElement(vElement: VElement): Element = document.createElement(vElement.type).apply {
    appendChild(
            document.createDocumentFragment().apply {
                vElement.children.forEach {
                    appendChild(processVNode(it))
                }
            }
    )
    vElement.attributes.forEach {
        setAttribute(it.key, it.value)
    }
    vElement.listeners.forEach {
        addEventListener(it.key, it.value)
    }
}

fun processVElementExpandFragments(vElement: VElement): Element = document.createElement(vElement.type).apply {
    appendChild(
            document.createDocumentFragment().apply {
                vElement.children.forEachExpandFragments {
                    appendChild(processVNodeExpandFragments(it))
                }
            }
    )
    vElement.attributes.forEach {
        setAttribute(it.key, it.value)
    }
    vElement.listeners.forEach {
        addEventListener(it.key, it.value)
    }
}

fun processVFragment(vFragment: VFragment): DocumentFragment = document.createDocumentFragment().apply {
    vFragment.children.forEach {
        appendChild(processVNode(it))
    }
}

fun processVFragmentExpandFragments(vFragment: VFragment): DocumentFragment = document.createDocumentFragment().apply {
    vFragment.children.forEachExpandFragments {
        appendChild(processVNodeExpandFragments(it))
    }
}
