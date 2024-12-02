import org.gradle.internal.jvm.Jvm // Jvm is part of the Gradle API

/*
tasks.register("brokenTask"){ // creates a new task
    println("this is executed at CONFIGURATION time")
}
// è rotto perchè esegue a tempo di configurazione
*/

tasks.register("HelloWorld"){
    doLast {
        println("Hello World!")
    }
}

tasks.getByName("HelloWorld"){
    doFirst {
        println("Configuration later, execution first")
    }
}

tasks.register<Exec>("printJavaVersion") {
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(javaExecutable, "-version")
    doLast{ println("$javaExecutable invocation complete") }
    doFirst{ println("ready to invoke $javaExecutable") }
}
/*
tasks.register<Exec>("compileJava"){
    val sources = findSources()
    if(sources.isNotEmpty()) {
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        commandLine(
            "$javacExecutable",
            "-d", "$buildDir/bin",
            *sources
        )
    }
}

fun findSources(): Array<String> = projectDir // From the project
    .listFiles { it: File -> it.isDirectory && it.name == "src" } // Find a folder named 'src'
    ?.firstOrNull() // If it's not there we're done
    ?.walk() // If it's there, iterate all its content (returns a Sequence<File>)
    ?.filter { it.extension == "java" } // Pick all Java files
    ?.map { it.absolutePath } // Map them to their absolute path
    ?.toList() // Sequences can't get converted to arrays, we must go through lists
    ?.toTypedArray() // Convert to Array<String>
    ?: emptyArray() // Yeah if anything's missing there are no sources
*/

// Gradle way to create a configuration
val compileClasspath by configurations.creating
dependencies {
    forEachLibrary {
        compileClasspath(files(it))
    }
    runtimeClasspath(files("build/bin"))
}

val compileJava = tasks.register<Exec>("compileJava") {
    val classpathFiles = compileClasspath.resolve()
    val sources = findSources()
    if(sources != null) {
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        val separator = ";"
        commandLine(
            "$javacExecutable", "-cp", classpathFiles.joinToString(separator),
            "-d", "bin", *sources
        )
    }
}
fun DependencyHandlerScope.forEachLibrary(todo: (String) -> Unit) {
    findLibraries().forEach{
        todo(it)
    }
}
fun findSources() = findFilesIn("src").withExtension("java")
fun findLibraries() = findFilesIn("lib").withExtension("jar")
fun findFilesIn(folder: String) = FinderInFolder(folder)

data class FinderInFolder(val folder: String) {
    fun withExtension(extension: String): Array<String> = projectDir
        .listFiles { it: File -> it.isDirectory && it.name == folder }
        ?.firstOrNull()
        ?.walk()
        ?.filter { it.extension == extension }
        ?.map { it.absolutePath }
        ?.toList()
        ?.toTypedArray()
        ?: emptyArray()
}

val runtimeClasspath by configurations.creating{
    extendsFrom(compileClasspath)
}


tasks.register<Exec>("runJava") {
    val classpathFiles = runtimeClasspath.resolve()
    val mainClass = "PrintException"
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(javaExecutable, "-cp", classpathFiles.joinToString(separator = separator), mainClass)
    dependsOn(compileJava)
}