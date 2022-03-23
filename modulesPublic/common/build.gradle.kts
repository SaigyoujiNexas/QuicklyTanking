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

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")

            javaCompileOptions {
                annotationProcessorOptions {
                    arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
                }
            }
        }
        buildFeatures {
            viewBinding = true
            dataBinding = true
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

    libraryC.forEach { (_, s2) -> implementation(s2) }
    libs.forEach { implementation(it) }
    apts.forEach { annotationProcessor(it) }
    tests.forEach { androidTestImplementation(it) }

    implementation("jp.wasabeef:glide-transformations:4.3.0")
    implementation("com.hjq:toast:8.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}