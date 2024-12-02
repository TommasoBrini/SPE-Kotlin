const val INDENT = "\t"
typealias Attribute = Pair<String, String> // Just to avoid writing Pair<String, String> every time

@DslMarker
annotation class HtmlTagMarker

// Element interface
interface Element {
    fun render(indent: String = ""): String
}

// RepeatableElement interface
interface RepeatableElement: Element

// Tag interface
interface Tag: Element {
    val name: String
    val children: List<Element>
    val attributes: Map<String, String>

}

//RepeatableTag interface
interface RepeatableTag: Tag, RepeatableElement

//TextElement interface
interface TextElement: RepeatableElement {
    val text: String
    override fun render(indent: String): String = "$indent$text\n"
}

//Implementation of the TextElement interface
data class Text(override val text: String): TextElement

// Abstract Implementation of the Tag interface, try to factor the common part of all Tags
abstract class AbstractTag(override val name: String, vararg attributes: Attribute): Tag {

    final override var children: List<Element> = emptyList() // override val with var
        private set(value) { field = value }
    final override val attributes: Map<String, String> = attributes.associate { it }

    fun registerElement(element: Element) {
        if(element is RepeatableElement || children.none { it::class == element::class }) {
            children = children + element
        } else {
            error("cannot repeat tag ${element::class.simpleName} multiple times:\n$element")
        }
    }

    final override fun render(indent: String) =
        """
        |$indent<$name${renderAttributes()}>
        |${renderChildren(indent + INDENT)}
        |$indent</$name>
        """.trimMargin().replace("""\R+""".toRegex(), "\n")

    private fun renderAttributes(): String = attributes.takeIf { it.isNotEmpty() }
        ?.map { (attribute, value) -> "$attribute=\"$value\" " }
        ?.joinToString(separator = " ", prefix = " ")
        ?: "" // Elvis operator

    private fun renderChildren(indent: String): String =
        children.map { it.render(indent) }.joinToString(separator = "\n")
}

// Entry point of the DSL
@HtmlTagMarker
class HTML(vararg attributes: Attribute = arrayOf()): AbstractTag("html", *attributes){
    fun head(configuration: Head.() -> Unit) = registerElement(Head().apply(configuration))
    fun body(vararg attributes: Attribute, configuration: Body.() -> Unit) = registerElement(Body(*attributes).apply(configuration))
}

fun html(vararg attributes: Attribute, init: HTML.() -> Unit): HTML = HTML(*attributes).apply(init)

// Implementation of the Tag interface => Head tag
@HtmlTagMarker
class Head(): AbstractTag("head"){
    fun title(configuration: Title.() -> Unit) = registerElement(Title().apply(configuration))
}

@HtmlTagMarker
abstract class TagWithText(name: String, vararg attributes: Attribute): AbstractTag(name, *attributes) {
    operator fun String.unaryMinus() = registerElement(Text(this))
}

class Title: TagWithText("title")

//class Body(vararg attributes: Attribute): TagWithText("body", *attributes)

abstract class BodyTag(name: String, vararg attributes: Attribute): TagWithText(name, *attributes) {
    // <a> and <p> can be nested everywhere in the body
    fun p(vararg attributes: Attribute, configuration: P.() -> Unit) =
        registerElement(P(*attributes).apply(configuration))
    fun a(href: String, vararg attributes: Attribute, configuration: Anchor.() -> Unit) =
        registerElement(Anchor(href, *attributes).apply(configuration))
}

class Body(vararg attributes: Attribute): BodyTag("body", *attributes)

class P(vararg attributes: Attribute): BodyTag("p", *attributes), RepeatableElement

class Anchor(
    href: String? = null,
    vararg attributes: Attribute
):  BodyTag("a", *(if (href == null) emptyArray() else arrayOf("href" to href)) + attributes),
    RepeatableElement

const val newline = "<br/>"

fun main() {
    println(html("lang" to "en") {
        head { title { -"A link to the unibo webpage" }}
        body {
            p("class" to "myCustomCssClass"){
                a(href = "https://www.unibo.it") {
                    -"Unibo Website"
                }
            }
        }
    }.render())

}













