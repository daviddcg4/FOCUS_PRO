plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.focuspro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.focuspro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //Configuracion para que se pueda acceder al modulo donde tengo el apikey
//        buildConfigField("String", "OPENAI_API_KEY", "\"${project.properties["OPENAI_API_KEY"]}\"")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(platform("com.google.firebase:firebase-bom:33.4.0")) // Solo una vez
    implementation("com.google.firebase:firebase-auth-ktx") // Usar a través de la BOM
    implementation("com.google.firebase:firebase-firestore-ktx") // Usar a través de la BOM
    implementation("com.google.firebase:firebase-analytics") // Usar a través de la BOM
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.constraintlayout.compose)


    //Para el view model
    implementation("androidx.compose.ui:ui:1.7.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    implementation (libs.coil.compose)







    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //retrofit para hacer las solicituydes http
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Para cprputines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    //Para base de datos
    implementation ("com.google.firebase:firebase-database:21.0.0")
    apply(plugin = "com.google.gms.google-services")


}
