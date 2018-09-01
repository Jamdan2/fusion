package fusion.dom

import fusion.ComponentBase
import fusion.HasAttributes
import fusion.Ktx
import fusion.vdom.VNode
import fusion.vdom.e

abstract class DomComponent(val type: String) : ComponentBase(), Ktx, HasAttributes<String?> {
    private val children: MutableList<ComponentBase> = arrayListOf()

    private val attributes: MutableMap<String, String> = mutableMapOf()

    override fun getAttribute(key: String): String? = attributes[key]

    override fun setAttribute(key: String, value: String?) {
        if (value != null) attributes[key] = value
        else removeAttribute(key)
    }

    override fun removeAttribute(key: String) {
        attributes.remove(key)
    }

    var accessKey by stringAttribute("accesskey")
    var classes: Set<String> by specialAttribute(
            "class",
            { thisRef, nameRef ->
                thisRef.getAttribute(nameRef)?.split(" ")?.toSet() ?: emptySet()
            },
            { thisRef, nameRef, value ->
                if (thisRef.getAttribute(nameRef) == null) thisRef.setAttribute(nameRef, "")
                value.forEachIndexed { index, className ->
                    thisRef.setAttribute(nameRef, thisRef.getAttribute(nameRef) + if (index == 0) className else " $className")
                }
            }
    )
    var isContentEditable by booleanAttribute("contenteditable")
    var textDir by enumAttribute("dir", TextDir::values)
    var isDraggable by booleanAttribute("draggable")
    var dropZone by enumAttribute("dropzone", DropZone::values)
    var isHidden by booleanAttribute("hidden")
    var id by stringAttribute("id")
    var lang by stringAttribute("lang")
    var isSpellChecked by booleanAttribute("spellcheck")
    var style by specialAttribute(
            "style",
            { thisRef, name ->
                thisRef.attributes[name]?.split(",")?.map {
                    it.split(":")
                }?.filter {
                    it.size == 2
                }?.map {
                    Pair(it.first(), it.last())
                }?.toMap() ?: emptyMap()
            },
            { thisRef, name, value ->
                if (thisRef.attributes[name] == null) thisRef.attributes[name] = ""
                value.forEach { keyValuePair ->
                    thisRef.attributes[name] += "${keyValuePair.key}:${keyValuePair.value},"
                }
                thisRef.attributes[name] = thisRef.attributes[name]!!.removeSuffix(",").also { println(it) }
            }
    )
    var tabIndex by intAttribute("tabindex")
    var title by stringAttribute("title")
    var isTranslated by booleanAttribute("translate", "yes", "no")

    override fun String.unaryPlus() {
        children += Text(this)
    }

    override fun ComponentBase.unaryPlus() {
        children += this
    }

    override fun VNode.unaryPlus() {
        children += Unknown(this)
    }

    override fun render(): VNode = e(type, attributes, *children.map { it.render() }.toTypedArray())
}
