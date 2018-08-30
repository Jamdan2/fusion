package fusion

import fusion.vdom.VNode
import fusion.vdom.f
import fusion.vdom.t

fun ktx(handler: Ktx.() -> Unit): VNode = object : Ktx {
    val children: ArrayList<VNode> = arrayListOf()

    override fun String.unaryPlus() {
        children.add(t(this))
    }

    override fun ComponentBase.unaryPlus() {
        children += render()
    }

    fun render(): VNode = if (children.size == 1) {
        children.first()
    } else {
        f(*children.toTypedArray())
    }
}.apply(handler).render()
