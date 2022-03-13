dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url= "https://jitpack.io")
        jcenter()
        mavenCentral()
    }
}
rootProject.name = "QuicklyTanking"
include(":app")
include(":modulesBase:libBase")
include(":modulesPublic:common")
include(":modulesCore:main")
include("modulesCore:setting")
include(":modulesCore:login")
