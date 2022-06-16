plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}
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
        applicationId = applicationIds["app"]
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions{
            annotationProcessorOptions {
                arguments += mapOf("AROUTER_MODULE_NAME" to project.name)
            }
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(javaVersion)
        targetCompatibility(javaVersion)
    }
    kotlinOptions{
        jvmTarget = JvmTarget
    }
    //support the view binding and dataBinding
    android.buildFeatures.viewBinding = true
    android.buildFeatures.dataBinding = true

    buildToolsVersion = androidC["buildToolsVersion"] as String
}

dependencies {

    if(!isDebug) {
        implementation(project(":modulesCore:main"))
        implementation(project(":modulesCore:login"))
    }
    implementation(project(":modulesPublic:common"))
    libraryC.forEach {(_, v)-> implementation(v) }
    apts.forEach { kapt(it) }
    libs.forEach { implementation(it) }
    libKtx.forEach { implementation(it) }
    implementation(libARouter)
    
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}