import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

val kotlinVrs: String by extra

plugins {
    id("kotlin2js") version "1.2.60"
}

group = "org.jamdan2.Kotlin-JS-Playground"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("http://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
    compile(kotlin("stdlib-js", kotlinVrs))
    testCompile("org.jetbrains.kotlin", "kotlin-test-js", kotlinVrs)
}

tasks {
    withType<Kotlin2JsCompile> {
        kotlinOptions.apply {
            metaInfo = true
            outputFile = "${project.buildDir.path}/js/${project.name}.js"
            sourceMap = true
            moduleKind = "commonjs"
            main = "call"
        }
    }
}
