package fusion

interface HasAttributes<A> {
    fun getAttribute(key: String): A

    fun setAttribute(key: String, value: A)

    fun removeAttribute(key: String)
}
