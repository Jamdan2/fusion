package fusion.dom

import fusion.ComponentBase
import fusion.vdom.VNode

class Unknown(private val value: VNode) : ComponentBase() {
    override fun render(): VNode = value
}
