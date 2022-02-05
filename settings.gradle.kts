dependencyResolutionManagement {
       repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()

    }
}
rootProject.name = "StarWars"
include(":app")
include(":data")
include(":ui-search-screen")
include(":domain")
include(":navigation")
include(":ui-detail-screen")
include(":ui-favorite-screen")
include(":ui-common")
