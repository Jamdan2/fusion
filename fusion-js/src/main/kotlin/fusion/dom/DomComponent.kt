package fusion.dom

import fusion.ComponentBase
import fusion.Ktx
import fusion.vdom.VNode
import fusion.vdom.e
import fusion.vdom.t
import org.w3c.dom.events.Event

abstract class DomComponent(val type: String) : ComponentBase(), Ktx {
    private val children: ArrayList<VNode> = arrayListOf()

    override fun render(): VNode = e(type, *children.toTypedArray())

    override fun String.unaryPlus() {
        children += t(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += render()
    }
}
