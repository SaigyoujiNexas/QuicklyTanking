plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

var versionCode: Int? = null
var versionName: String? = null
    android {
        compileSdk = androidC["compileSdk"] as Int
        defaultConfig {
            minSdk = androidC["minSdk"] as Int
            targetSdk = androidC["targetSdk"] as Int
            versionCode = androidC["versionCode"] as Int
            versionName = androidC["versionName"] as String

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            val release by getting {
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
    }

dependencies {

    api(project(":modulesBase:libBase"))
    api(libARouter)

    implementation(libHilt)
    annotationProcessor(libHiltCompiler)
    implementation(libHiltLifeCycle)
    annotationProcessor(libHiltAndroidXCompiler)

    implementation("com.hjq:toast:8.0")

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}