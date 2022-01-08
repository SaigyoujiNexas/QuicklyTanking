import org.gradle.api.JavaVersion

val isDebug = false
val androidC = mapOf(
"compileSdk" to 31,
"minSdk" to 27,
"targetSdk" to 31,
"buildToolsVersion" to "31.0.0",
"versionCode" to 1,
"versionName" to "1.0"
)
val libraryC = mapOf(
"appcompat" to  "androidx.appcompat:appcompat:1.4.0",
"material" to  "com.google.android.material:material:1.4.0",
"constraintlayout" to "androidx.constraintlayout:constraintlayout:2.1.2",
    "core-ktx" to "androidx.core:core-ktx:1.7.0"
)
val libARouter = "com.alibaba:arouter-api:1.5.2"
val libARouterCompiler = "com.alibaba:arouter-compiler:1.5.2"

val applicationIds = mapOf(
    "app" to "com.example.quicklytanking",
    "main" to "com.example.modulescore.main",
    "login" to "com.example.modulescore.login"
)

val javaVersion = JavaVersion.VERSION_11

val kotlinVersion = "1.6.10"
