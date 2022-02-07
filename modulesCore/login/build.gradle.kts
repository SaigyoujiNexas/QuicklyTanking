plugins {
    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")
    id("dagger.hilt.android.plugin")
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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
            }
        }
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

    sourceSets["main"].manifest.srcFile {
        if (isDebug)
            "src/main/debug/AndroidManifest.xml"
        else
            "src/main/AndroidManifest.xml"
    }
}

dependencies {

    implementation(project(":modulesPublic:common"))

    annotationProcessor(libARouterCompiler)

    implementation(libHilt)
    annotationProcessor(libHiltCompiler)
    implementation(libHiltLifeCycle)
    annotationProcessor(libHiltAndroidXCompiler)

    implementation("androidx.navigation:navigation-compose:2.4.0-beta02")

    androidTestImplementation(libraryC["nav-test"] as Any)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}