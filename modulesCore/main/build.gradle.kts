plugins {
    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
}
var applicationId : String? = null
var versionCode: Int? = null
var versionName: String? = null
android{
    signingConfigs {
        getByName("debug") {
            storeFile = file("./Run00.jks")
            storePassword = "llllll"
            keyAlias = "key0"
            keyPassword = "llllll"
        }
    }
    compileSdk = androidC["compileSdk"] as Int
    defaultConfig {
        if(isDebug)
            applicationId = applicationIds["main"]
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String

        buildFeatures{
            viewBinding = true
            dataBinding = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.example.modulescore.main"
    sourceSets["main"].manifest.srcFile {
        if (isDebug)
            "src/main/debug/AndroidManifest.xml"
        else
            "src/main/AndroidManifest.xml"
    }

}

dependencies {
    val roomVersion = "2.4.2"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt) kapt("androidx.room:room-compiler:$roomVersion")
    // To use Kotlin Symbolic Processing (KSP)
    //ksp("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    implementation(project(":modulesPublic:common"))
    implementation("org.greenrobot:eventbus:3.1.1")
    libraryC.forEach { (_, s2) -> implementation(s2) }
    libs.forEach { implementation(it) }
    apts.forEach { annotationProcessor(it) }
    tests.forEach { androidTestImplementation(it) }

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

