package fusion.dom

import fusion.ComponentBase
import fusion.Ktx
import fusion.vdom.VNode
import fusion.vdom.f
import fusion.vdom.t

class Fragment : ComponentBase(), Ktx {
    val children: ArrayList<VNode> = arrayListOf()

    override fun String.unaryPlus() {
        children += t(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += render()
    }

    override fun VNode.unaryPlus() {
        children += this
    }

    override fun render(): VNode = f(*children.toTypedArray())
}
