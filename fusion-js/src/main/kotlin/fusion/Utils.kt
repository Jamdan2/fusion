package fusion

import fusion.dom.Text
import fusion.dom.Unknown
import fusion.vdom.VNode
import fusion.vdom.f
import fusion.vdom.processVNodeExpandFragments
import org.w3c.dom.Node
import kotlin.properties.Delegates

private var rootNode by Delegates.notNull<Node>()

fun ktx(handler: Ktx.() -> Unit): VNode = object : Ktx {
    val children: MutableList<ComponentBase> = arrayListOf()

    override fun String.unaryPlus() {
        children += Text(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += this
    }

    override fun VNode.unaryPlus() {
        children += Unknown(this)
    }

    fun render(): VNode = if (children.size == 1) {
        children.first().render()
    } else {
        f(*children.map { it.render() }.toTypedArray())
    }
}.apply(handler).render()

fun render(rootNode: Node, handler: Ktx.() -> Unit) {
    fusion.rootNode = rootNode
    rootNode.appendChild(processVNodeExpandFragments(ktx(handler)))
}
