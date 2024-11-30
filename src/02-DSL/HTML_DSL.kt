interface Element {
    fun render(indent: String = ""): String
}

interface RepeatableElement: Element

interface Tag: Element {
    val name: String
    val children: List<Element>
    val attributes: Map<String, String>

}

interface RepeatableTag: Tag, RepeatableElement

interface TextElement: RepeatableElement {
    val text: String
    override fun render(indent: String): String = "$indent$text\n"
}
