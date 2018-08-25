group = "org.fusion"
version = "1.0-SNAPSHOT"

project.subprojects {
    project.apply { from("../versions.gradle.kts") }
}
