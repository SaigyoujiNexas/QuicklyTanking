pluginManagement{
    repositories{
        gradlePluginPortal()
        google()

        maven (url = "https://jitpack.io")
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://jitpack.io")

        mavenCentral()
    }
}
rootProject.name = "SafeAndRun"
include(":app")
include(":modulesBase:libBase")
include(":modulesPublic:common")
include(":modulesCore:main")
include("modulesCore:setting")
include(":modulesCore:login")
include(":modulesCore:community")
include(":modulesCore:shop")
