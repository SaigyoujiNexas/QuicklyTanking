dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url= "https://jitpack.io")
        jcenter()
        mavenCentral()
        maven(url="https://jitpack.io")
    }
}
rootProject.name = "QuicklyTanking"
include(":app")
include(":modulesBase:libBase")
include(":modulesPublic:common")
include(":modulesCore:main")
include(":modulesCore:login")
include("modulesCore:setting")
