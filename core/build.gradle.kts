plugins {
  id("org.gradle.java.experimental-jigsaw") version "0.1.1"
  application
}

//javaModule.name = "config"


extra.set("okhttpVersion", "3.9.1")
extra.set("jsonVersion", "20170516")
extra.set("jacksonVersion", "2.9.7")
extra.set("javafxVersion", "11.0.2")


dependencies {
  compile("org.openjfx:javafx-controls:${extra.get("javafxVersion")}:linux")
  compile("org.openjfx:javafx-graphics:${extra.get("javafxVersion")}:linux")
  compile("org.openjfx:javafx-base:${extra.get("javafxVersion")}:linux")

  implementation("com.squareup.okhttp3:okhttp:${extra.get("okhttpVersion")}")
  implementation("org.json:json:${extra.get("jsonVersion")}")
  implementation("commons-io:commons-io:2.5")
  implementation("com.fasterxml.jackson.core:jackson-databind:${extra.get("jacksonVersion")}")

  compile(project(":api"))
  compile(project(":impl"))
}
