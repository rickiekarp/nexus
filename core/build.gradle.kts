dependencies {
  implementation("com.squareup.okhttp3:okhttp:${rootProject.extra.get("okhttpVersion")}")
  implementation("org.json:json:${rootProject.extra.get("jsonVersion")}")
  implementation("commons-io:commons-io:2.5")
  implementation("com.fasterxml.jackson.core:jackson-databind:${rootProject.extra.get("jacksonVersion")}")
}
