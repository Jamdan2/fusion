package fusion

import fusion.vdom.VNode

abstract class ComponentBase {
    abstract fun render(): VNode
}
