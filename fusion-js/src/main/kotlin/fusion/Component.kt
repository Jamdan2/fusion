package fusion

import fusion.dom.Text
import fusion.dom.Unknown
import fusion.vdom.VNode

abstract class Component : ComponentBase(), Ktx {
    protected val children: MutableList<ComponentBase> = mutableListOf()

    override fun String.unaryPlus() {
        children += Text(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += this
    }

    override fun VNode.unaryPlus() {
        children += Unknown(this)
    }

    final override fun render(): VNode = ktx { render() }

    abstract fun Ktx.render()
}
