plugins {
    id("com.android.application")
}
android {
    compileSdk = androidC["compileSdk"] as Int

    defaultConfig {
        applicationId = applicationIds["app"]
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String
        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mapOf("AROUTER_MODULE_NAME" to project.name))
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    buildToolsVersion = "31.0.0"
}

dependencies {

    implementation(project(":modulesCore:main"))
    implementation(project(":modulesCore:login"))
    implementation(project(":modulesBase:libBase"))

    implementation(project(":modulesPublic:common"))
    implementation(project(":modulesPublic:repository"))
    annotationProcessor(libARouterCompiler)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}