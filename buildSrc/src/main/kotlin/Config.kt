import org.gradle.api.JavaVersion

const val isDebug = false
val androidC = mapOf(
"compileSdk" to 31,
"minSdk" to 27,
"targetSdk" to 31,
"buildToolsVersion" to "31.0.0",
"versionCode" to 1,
"versionName" to "1.0"
)

const val nav_version = "2.3.5"


val libraryC = mapOf(
"appcompat" to  "androidx.appcompat:appcompat:1.4.0",
"material" to  "com.google.android.material:material:1.4.0",
"constraintlayout" to "androidx.constraintlayout:constraintlayout:2.1.2",
    "core-ktx" to "androidx.core:core-ktx:1.7.0",
    "nav-fragment" to "androidx.navigation:navigation-fragment:$nav_version",
    "nav-ui" to "androidx.navigation:navigation-ui:$nav_version",
    "nav-dnmc-fragment" to "androidx.navigation:navigation-dynamic-features-fragment:$nav_version",
    "nav-test" to "androidx.navigation:navigation-testing:$nav_version"
)
const val libARouter = "com.alibaba:arouter-api:1.5.2"
const val libARouterCompiler = "com.alibaba:arouter-compiler:1.5.2"

const val libHilt = "com.google.dagger:hilt-android:2.40.5"
const val libHiltCompiler = "com.google.dagger:hilt-android-compiler:2.40.5"
const val libHiltLifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
const val libHiltAndroidXCompiler = "androidx.hilt:hilt-compiler:1.0.0"


const val libRetrofit = "com.squareup.retrofit2:retrofit:2.9.0"
const val libOkHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
const val libRxJava = "io.reactivex.rxjava3:rxjava:3.1.3"
const val libRxAndroid = "io.reactivex.rxjava3:rxandroid:3.0.0"
const val libRetrofit2RxJava = "com.squareup.retrofit2:adapter-rxjava3:2.9.0"
const val libRetrofit2Gson = "com.squareup.retrofit2:converter-gson:2.9.0"

val applicationIds = mapOf(
    "app" to "com.example.quicklytanking",
    "main" to "com.example.modulescore.main",
    "login" to "com.example.modulescore.login"
)

val javaVersion = JavaVersion.VERSION_11

val kotlinVersion = "1.6.10"
const val JvmTarget = "11"
