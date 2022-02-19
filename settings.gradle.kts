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
include(":modulesBase:StackBlur")
include(":modulesPublic:common")
include(":modulesCore:main")
include(":modulesCore:login")
include("modulesCore:setting")
include(":modulesCore:composetest")
