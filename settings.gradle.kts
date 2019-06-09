rootProject.name = "Backend"

include("Foundation")
include("HomeServer")
include("LoginServer")
include("WebsiteBackend")

for (project in rootProject.children) {
    when (project.name) {
        "WebsiteBackend" -> {
            project.apply {
                projectDir = file("Website/$name")
                buildFileName = "build.gradle"
            }
        }
        else -> { // Note the block
            project.apply {
                buildFileName = "build.gradle"
            }
        }
    }


}