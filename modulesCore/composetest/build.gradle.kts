plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = androidC["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.saigyouji.android.composetest"
        minSdk = androidC["minSdk"] as Int
        targetSdk = androidC["targetSdk"] as Int
        versionCode = androidC["versionCode"] as Int
        versionName = androidC["versionName"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt{
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}
dependencies {
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra["compose_version"]}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.android.material:material:1.5.0")

    libraryC.forEach { (_, v) -> implementation(v)}

    libKtx.forEach { implementation(it) }
    implementation(project(":modulesPublic:common"))
    libs.forEach { implementation(it) }
    apts.forEach { kapt(it) }
    tests.forEach { androidTestImplementation(it) }
    librariesDebug.forEach { debugImplementation(it)}

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
}