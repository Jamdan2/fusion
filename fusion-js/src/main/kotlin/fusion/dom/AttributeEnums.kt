package fusion.dom

enum class TextDir(override val value: String) : DomAttributeEnum {
    RTL("rtl"),
    LTR("ltr"),
    AUTO("auto")
}

enum class DropZone(override val value: String) : DomAttributeEnum {
    COPY("copy"),
    MOVE("move"),
    LINK("link")
}
