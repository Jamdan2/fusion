package fusion

import fusion.vdom.VNode

interface Ktx {
    operator fun String.unaryPlus()

    operator fun ComponentBase.unaryPlus()

    operator fun VNode.unaryPlus()
}
