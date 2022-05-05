plugins {

    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")

    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
var applicationId : String? = null
var versionCode: Int? = null
var versionName: String? = null
android {
    compileSdk = androidC["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.saigyouji.android.composetest"
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = JvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets["main"].manifest.srcFile {
        if (isDebug)
            "src/main/debug/AndroidManifest.xml"
        else
            "src/main/AndroidManifest.xml"
    }
}

kapt{
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
dependencies {

    implementation(project(":modulesPublic:common"))
    libCompose.forEach { implementation(it) }
    libraryC.forEach { (_, v) -> implementation(v)}
    libKtx.forEach { implementation(it) }
    libs.forEach { implementation(it) }
    apts.forEach { kapt(it) }
    tests.forEach { androidTestImplementation(it) }
    librariesDebug.forEach { debugImplementation(it)}

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
}