package fusion

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Attribute<T : HasAttributes<P>, A, P>(
        private val name: String,
        val getter: (thisRef: T, nameRef: String) -> A,
        val setter: (thisRef: T, nameRef: String, value: A) -> Unit
) : ReadWriteProperty<T, A> {
    override fun getValue(thisRef: T, property: KProperty<*>): A {
        return getter(thisRef, name)
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: A) {
        setter(thisRef, name, value)
    }
}
