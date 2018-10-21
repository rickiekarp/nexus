rootProject.name = "JavaFXApps"

include("AppUpdater")
include("BotManager")
include("BotLib")
include("FilelistCreator")
include("QAAccountManager")
include("SHA1Pass")
include("api")
include("impl")
include("core")

for (project in rootProject.children) {
    when (project.name) {
        "BotLib" -> {
            project.apply {
                projectDir = file("BotManager/$name")
            }
        }
    }
    project.apply {
        buildFileName = "build.gradle.kts"
    }
}