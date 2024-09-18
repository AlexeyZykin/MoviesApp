plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.alexisdev.moviesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexisdev.moviesapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))
    implementation(project(":feature:film_catalog"))
    implementation(project(":feature:film_details"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
}