plugins {
    if(isDebug)
        id("com.android.application")
    else
        id("com.android.library")
}
var applicationId : String? = null
var versionCode: Int? = null
var versionName: String? = null
android{
    compileSdk = androidC["compileSdk"] as Int
    defaultConfig {
        if(isDebug)
            applicationId = applicationIds["main"]
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
            }
        }
        sourceSets["main"].manifest.srcFile {
            if (isDebug)
                "src/main/debug/AndroidManifest.xml"
            else
                "src/main/AndroidManifest.xml"
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
}

dependencies {

    implementation(project(":modulesBase:libBase"))
    implementation(project(":modulesPublic:repository"))

    annotationProcessor(libARouterCompiler)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}