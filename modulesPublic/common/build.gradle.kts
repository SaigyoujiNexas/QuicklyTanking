plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

var versionCode: Int? = null
var versionName: String? = null
    android {
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
            minSdk = androidC["minSdk"] as Int
            targetSdk = androidC["targetSdk"] as Int

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
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
        namespace = "com.xupt.safeAndRun.modulespublic.common"
    }


dependencies {

    api(project(":modulesBase:libBase"))

    libKtx.forEach { implementation(it) }
    libraryC.forEach { (_, s2) -> implementation(s2) }
    libs.forEach { implementation(it) }
    apts.forEach { kapt(it) }
    tests.forEach { androidTestImplementation(it) }

    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}