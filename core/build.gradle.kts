plugins {
  id("org.gradle.java.experimental-jigsaw") version "0.1.1"
}

javaModule {
  setName("core")
}

dependencies {
  compile("org.openjfx:javafx-controls:${rootProject.extra.get("javafxVersion")}:linux")
  compile("org.openjfx:javafx-graphics:${rootProject.extra.get("javafxVersion")}:linux")
  compile("org.openjfx:javafx-base:${rootProject.extra.get("javafxVersion")}:linux")

  implementation("com.squareup.okhttp3:okhttp:${rootProject.extra.get("okhttpVersion")}")
  implementation("org.json:json:${rootProject.extra.get("jsonVersion")}")
  implementation("commons-io:commons-io:2.5")
  implementation("com.fasterxml.jackson.core:jackson-databind:${rootProject.extra.get("jacksonVersion")}")

  compile(project(":api"))
  compile(project(":impl"))
}
