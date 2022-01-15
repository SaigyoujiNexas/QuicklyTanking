plugins {
    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
    id("kotlin-kapt")
}
var applicationId :String? = null
var versionCode : Int? = null
var versionName : String? = null
android {
    compileSdk = androidC["compileSdk"] as Int

    defaultConfig {
        applicationId = applicationIds["login"]
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting{
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(javaVersion)
        targetCompatibility(javaVersion)
    }
    kotlinOptions {
        jvmTarget = JvmTarget
    }

    sourceSets["main"].manifest.srcFile {
        if (isDebug)
            "src/main/debug/AndroidManifest.xml"
        else
            "src/main/AndroidManifest.xml"
    }
}
kapt {
    arguments  {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
dependencies {

    implementation(project(":modulesBase:libBase"))
    implementation(project(":modulesPublic:common"))

    implementation(libARouter)
    kapt(libARouterCompiler)

    implementation(libHilt)
    kapt(libHiltCompiler)
    implementation(libHiltLifeCycle)
    kapt(libHiltAndroidXCompiler)

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}