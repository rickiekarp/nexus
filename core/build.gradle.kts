plugins {
  id("org.gradle.java.experimental-jigsaw") version "0.1.1"
  application
}

//javaModule.name = "config"


extra.set("okhttpVersion", "3.9.0")
extra.set("jsonVersion", "20170516")
extra.set("jacksonVersion", "2.9.7")


dependencies {
  implementation("org.openjfx:javafx-controls:11:linux")
  implementation("org.openjfx:javafx-graphics:11:linux")
  implementation("org.openjfx:javafx-base:11:linux")

  implementation("com.squareup.okhttp:okhttp:2.7.5")
  implementation("org.json:json:${extra.get("jsonVersion")}")
  implementation("commons-io:commons-io:2.5")
  implementation("com.fasterxml.jackson.core:jackson-databind:${extra.get("jacksonVersion")}")

  compile(project(":api"))
  compile(project(":impl"))
}
