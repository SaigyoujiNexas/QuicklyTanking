import org.gradle.api.JavaVersion

const val isDebug = false

val androidC = mapOf(
    "compileSdk" to 33,
    "minSdk" to 27,
    "targetSdk" to 33,
    "buildToolsVersion" to "32.0.0",
    "versionCode" to 1,
    "versionName" to "1.0"
)

const val nav_version = "2.5.3"
const val truth_version = "1.0"
const val room_version = "2.4.2"
const val compose_version = "1.4.3"
const val hilt_version = "2.46.1"
const val libUCropper = "com.github.yalantis:ucrop:2.2.6"

const val libAMap3DMap = "com.amap.api:3dmap:9.7.0"
const val libAMapLocation = "com.amap.api:location:6.3.0"

const val libHttpLogger = "com.squareup.okhttp3:logging-interceptor:4.9.3"
//const val libARouter = "com.alibaba:arouter-api:1.5.2"
//const val libARouterCompiler = "com.alibaba:arouter-compiler:1.5.2"

const val libHilt = "com.google.dagger:hilt-android:$hilt_version"
const val libHiltCompiler = "com.google.dagger:hilt-android-compiler:$hilt_version"
//const val libHiltLifeCycle = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
const val libHiltAndroidCompiler = "androidx.hilt:hilt-compiler:1.0.0"

const val libStartUp = "androidx.startup:startup-runtime:1.1.0"
const val libRetrofit = "com.squareup.retrofit2:retrofit:2.9.0"
const val libRetrofit2Gson = "com.squareup.retrofit2:converter-gson:2.9.0"
const val libRetrofit2Moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"

const val libGlide = "com.github.bumptech.glide:glide:4.12.0"
const val libGlideCompiler = "com.github.bumptech.glide:compiler:4.12.0"
const val libGlideOkhttp3 = "com.github.bumptech.glide:okhttp3-integration:4.12.0"

const val libRxPermissions = "com.github.qicodes:rxpermissions:1.3.0"
const val libStatusBarUtil = "com.jaeger.statusbarutil:library:1.5.1"
const  val libRecyclerAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"
const  val libTicker = "com.robinhood.ticker:ticker:2.0.1"
const val libCoil = "io.coil-kt:coil:1.4.0"
const val libCoilCompose = "io.coil-kt:coil-compose:2.0.0-rc01"

const val libEventbus = "org.greenrobot:eventbus:3.1.1"
const val libRoom =  "androidx.room:room-runtime:$room_version"
const val libRoomCompiler = "androidx.room:room-compiler:$room_version"
const val libRoomKtx = "androidx.room:room-ktx:$room_version"

const val libMD3 = "com.google.android.material:material:1.5.0"
const val libCoroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"

const val libComposeConstraintlayout = "androidx.constraintlayout:constraintlayout-compose:1.0.0"
const val libComposeUI = "androidx.compose.ui:ui:$compose_version"
const val libComposeMaterial = "androidx.compose.material:material:$compose_version"
const val libComposePreview = "androidx.compose.ui:ui-tooling-preview:$compose_version"
const val libComposeActivity = "androidx.activity:activity-compose:1.7.2"
const val libComposeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:$compose_version"
const val libHiltNavCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"


//--------------------Navigation--------------------
val libNavigationFragment = "androidx.navigation:navigation-fragment:$nav_version"
val libNavigationUI = "androidx.navigation:navigation-ui:$nav_version"
val libNavgationKtxFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
val libNavigationKtxUI = "androidx.navigation:navigation-ui-ktx:$nav_version"
val libNavigationDynamicFeaturesFragment = "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
val androidNavigationTesting = "androidx.navigation:navigation-testing:$nav_version"
val libComposeNavigation = "androidx.navigation:navigation-compose:$nav_version"

val libraryC = mapOf(
    "appcompat" to "androidx.appcompat:appcompat:1.6.1",
    "constraintlayout" to "androidx.constraintlayout:constraintlayout:2.1.4",
    "navigation-fragment" to libNavigationFragment,
    "navigation-ui" to libNavigationUI,
    "navigation-dynamic-features-fragment" to libNavigationDynamicFeaturesFragment,
    "md3" to libMD3
)

val libKtx = listOf(
    "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-beta01",
    "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1",
    "androidx.lifecycle:lifecycle-livedata-ktx:2.5.0-beta01",
    "androidx.core:core-ktx:1.10.1",
    "androidx.navigation:navigation-fragment-ktx:$nav_version",
    "androidx.navigation:navigation-ui-ktx:$nav_version",
    libCoroutine,
    libRoomKtx,
    libNavigationKtxUI,
    libNavgationKtxFragment

    )
val librariesDebug = listOf(
    "com.google.truth:truth:$truth_version",
    "androidx.compose.ui:ui-tooling:$compose_version",
)

val libCompose = listOf(
    libComposeNavigation,
    libCoilCompose,
    libComposeConstraintlayout,
    libComposeUI,
    libComposeMaterial,
    libComposePreview,
    libComposeActivity,
    libComposeViewModel,
    libHiltNavCompose
)
val apts = listOf(
//    libARouterCompiler,
    libHiltCompiler,
    libHiltAndroidCompiler,
    libGlideCompiler,
    libRoomCompiler
)
val libs = listOf(
    libUCropper,
    libStartUp,
    libHilt,
//    libHiltLifeCycle,
    libHttpLogger,
    libRetrofit,
    libRetrofit2Gson,
    libGlide,
    libGlideOkhttp3,
    libRxPermissions,
    libAMap3DMap,
    libTicker,
    libCoil,
    libTicker,
    libEventbus,
    libRoom
)
val tests = listOf(
    "androidx.navigation:navigation-testing:$nav_version",
    "androidx.compose.ui:ui-test-junit4:$compose_version"
)
val applicationIds = mapOf(
    "app" to "com.xupt.safeAndRun",
    "main" to "com.xupt.safeAndRun.example.modulescore.main",
    "login" to "com.xupt.safeAndRun.example.modulescore.login"
)

val javaVersion = JavaVersion.VERSION_17

const val kotlinVersion = "1.8.10"
const val JvmTarget = "17"