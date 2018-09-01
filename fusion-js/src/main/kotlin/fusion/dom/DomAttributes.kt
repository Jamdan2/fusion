package fusion.dom

import fusion.Attribute

fun stringAttribute(name: String): Attribute<DomComponent, String?, String?> = Attribute(
        name,
        { thisRef, nameRef ->
            thisRef.getAttribute(nameRef)
        },
        { thisRef, nameRef, value ->
            thisRef.setAttribute(nameRef, value)
        }
)

fun intAttribute(name: String): Attribute<DomComponent, Int?, String?> = Attribute(
        name,
        { thisRef, nameRef ->
            thisRef.getAttribute(nameRef)?.toInt()
        },
        { thisRef, nameRef, value ->
            thisRef.setAttribute(nameRef, value.toString())
        }
)

fun booleanAttribute(
        name: String,
        trueAlias: String = "true",
        falseAlias: String = "false"
): Attribute<DomComponent, Boolean?, String?> = Attribute(
        name,
        { thisRef, nameRef ->
            when (thisRef.getAttribute(nameRef)) {
                trueAlias -> true
                falseAlias -> false
                else -> null
            }
        },
        { thisRef, nameRef, value ->
            thisRef.setAttribute(nameRef, when (value) {
                true -> trueAlias
                false -> falseAlias
                else -> null
            })
        }
)

fun <A> enumAttribute(
        name: String,
        values: () -> Array<A>
): Attribute<DomComponent, A?, String?> where A : Enum<A>, A : DomAttributeEnum = Attribute(
        name,
        getter@{ thisRef, nameRef ->
            with (thisRef.getAttribute(nameRef)) {
                values().forEach { if (it.value == this) return@getter it }
            }
            null
        },
        { thisRef, nameRef, value ->
            thisRef.setAttribute(nameRef, value?.value)
        }
)

fun <A> specialAttribute(
        name: String,
        getter: (thisRef: DomComponent, nameRef: String) -> A,
        setter: (thisRef: DomComponent, nameRef: String, value: A) -> Unit
): Attribute<DomComponent, A, String?> = Attribute(name, getter, setter)
