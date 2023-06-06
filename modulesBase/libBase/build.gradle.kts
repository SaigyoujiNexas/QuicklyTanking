plugins {
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    id("com.android.library")
}
var versionCode : Int? = null
var versionName : String? = null
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
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }
    kotlinOptions{
        jvmTarget = JvmTarget
    }
        compileOptions {
            sourceCompatibility(javaVersion)
            targetCompatibility(javaVersion)
        }
    namespace = "com.xupt.safeAndRun.modulesbase.libbase"
}

    dependencies {

        libraryC.forEach{ (_, v) -> implementation(v)}
        libs.forEach { s -> implementation(s) }
        tests.forEach { t -> androidTestImplementation(t) }
        apts.forEach { s -> annotationProcessor(s) }


        testImplementation("junit:junit:4.+")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    }

