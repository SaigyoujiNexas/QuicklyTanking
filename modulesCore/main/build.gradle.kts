plugins {
    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")
    id("dagger.hilt.android.plugin")
}
var applicationId : String? = null
var versionCode: Int? = null
var versionName: String? = null
android{
    signingConfigs {
        getByName("debug") {
            storeFile = file("E:\\learn\\Android\\Run00.jks")
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
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
            }
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
        sourceCompatibility(javaVersion)
        targetCompatibility(javaVersion)
    }
    sourceSets["main"].manifest.srcFile {
        if (isDebug)
            "src/main/debug/AndroidManifest.xml"
        else
            "src/main/AndroidManifest.xml"
    }

}


dependencies {

    implementation(project(":modulesPublic:common"))
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("org.greenrobot:eventbus:3.1.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    val roomVersion = "2.4.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    libraryC.forEach { (_, s2) -> implementation(s2) }
    libs.forEach { implementation(it) }
    apts.forEach { annotationProcessor(it) }
    tests.forEach { androidTestImplementation(it) }

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}