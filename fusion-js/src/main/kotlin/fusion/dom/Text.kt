package fusion.dom

import fusion.ComponentBase
import fusion.vdom.VNode
import fusion.vdom.t

class Text(val value: String) : ComponentBase() {
    override fun render(): VNode = t(value)
}
