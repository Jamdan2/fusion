package fusion.dom

import fusion.ComponentBase
import fusion.Ktx
import fusion.vdom.VNode
import fusion.vdom.e
import fusion.vdom.t
import org.w3c.dom.events.Event

abstract class DomComponent(val type: String) : ComponentBase(), Ktx {
    private val children: MutableList<ComponentBase> = arrayListOf()

    override fun String.unaryPlus() {
        children += Text(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += this
    }

    override fun VNode.unaryPlus() {
        children += Unknown(this)
    }

    override fun render(): VNode = e(type, *children.map { it.render() }.toTypedArray())
}
