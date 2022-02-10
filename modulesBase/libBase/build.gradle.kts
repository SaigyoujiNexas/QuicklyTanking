
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}
var versionCode : Int? = null
var versionName : String? = null
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
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
        compileOptions {
            sourceCompatibility(javaVersion)
            targetCompatibility(javaVersion)
        }
    }

    dependencies {

        libraryC.forEach{(_, v) -> implementation(v)}
        libs.forEach { s -> implementation(s) }
        tests.forEach { t -> androidTestImplementation(t) }
        apts.forEach { s -> implementation(s) }




        testImplementation("junit:junit:4.+")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
        implementation("androidx.core:core-ktx:+")
    }
