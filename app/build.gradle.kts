plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.weather_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weather_app"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)  // Библиотека AppCompat
    implementation(libs.material)  // Библиотека Material Design
    implementation(libs.activity)  // Библиотека для Activity
    implementation(libs.constraintlayout)  // Библиотека для ConstraintLayout
    testImplementation(libs.junit)  // Библиотека для юнит тестов
    androidTestImplementation(libs.ext.junit)  // Библиотека для UI тестов
    androidTestImplementation(libs.espresso.core)  // Библиотека для тестирования UI

    // Навигация
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")

    // Жизненный цикл (Lifecycle)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    // Корутин (для асинхронных операций)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // Retrofit для работы с API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson для работы с JSON
    implementation("com.google.code.gson:gson:2.8.8")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("com.google.android.material:material:1.10.0")

}
