import org.gradle.internal.jvm.Jvm // Jvm is part of the Gradle API

tasks.register("brokenTask"){ // creates a new task
    println("this is executed at CONFIGURATION time")
}

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