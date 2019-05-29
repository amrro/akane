package app.akane

object Deps {

    object Versions {
        val ktlint = "0.29.0"
    }

    object Kotlin {
        private const val version = "1.3.31"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }


    object Google {
        val material = "com.google.android.material:material:1.1.0-alpha05"
        val firebaseCore = "com.google.firebase:firebase-core:16.0.4"
        val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.9.8"
        val gmsGoogleServices = "com.google.gms:google-services:4.2.0"
        val fabricPlugin = "io.fabric.tools:gradle:1.27.0"
    }

    object AndroidX {
        val appcompat = "androidx.appcompat:appcompat:1.1.0-alpha05"
        val recyclerView = "androidx.recyclerview:recyclerview:1.1.0-alpha05"
        val corekts = "androidx.core:core-ktx:1.2.0-alpha01"
        val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta1"
        val legacy = "androidx.legacy:legacy-support-v4:1.0.0"
        val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0-alpha03"
    }

    object LeakCanary {
        private const val version = "1.6.3"
        val debug = "com.squareup.leakcanary:leakcanary-android:$version"
        val release = "com.squareup.leakcanary:leakcanary-android-no-op:$version"
        val fragment = "com.squareup.leakcanary:leakcanary-support-fragment:$version"
    }


    object Facebook {
        val frescoVersion = "1.13.0"
        val fresco = "com.facebook.fresco:fresco:$frescoVersion"
        val frescoGif = "com.facebook.fresco:animated-gif:$frescoVersion"
    }

    object Glide {
        private const val version = "4.8.0"
        val glide = "com.github.bumptech.glide:glide:$version"
        val compiler = "com.github.bumptech.glide:compiler:$version"
    }


    object Dagger {
        private const val version = "2.22.1"
        val dagger = "com.google.dagger:dagger:$version"
        val androidSupport = "com.google.dagger:dagger-android-support:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
        val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object AssistedInject {
        private const val version = "0.4.0"
        val annotationDagger2 = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        val processorDagger2 = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }

    object Paging {
        private const val version = "2.1.0"
        val common = "androidx.paging:paging-common-ktx:$version"
        val runtime = "androidx.paging:paging-runtime-ktx:$version"
    }

    object Coroutines {
        private const val version = "1.2.1"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Room {
        private const val version = "2.1.0-beta01"
        val common = "androidx.room:room-common:$version"
        val runtime = "androidx.room:room-runtime:$version"
        val coroutine = "androidx.room:room-ktx:$version"
        val compiler = "androidx.room:room-compiler:$version"
        val testing = "androidx.room:room-testing:$version"
    }

    object Navigation {
        private const val version = "2.1.0-alpha04"
        val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        val navigationUI = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Fragment {
        private const val version = "1.1.0-alpha09"
        val fragment = "androidx.fragment:fragment:$version"
        val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
    }

    object Lifecycle {
        private const val version = "2.2.0-alpha01"
        val extension = "androidx.lifecycle:lifecycle-extensions:$version"
        val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    }

    object API {
        val jraw = "net.dean.jraw:JRAW-Android:1.1.0"
    }

    object Epoxy {
        private const val version = "3.5.1"
        val epoxy = "com.airbnb.android:epoxy:$version"
        val paging = "com.airbnb.android:epoxy-paging:$version"
        val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        val processor = "com.airbnb.android:epoxy-processor:$version"
    }

    object Helpers {
        private const val timberVersion = "4.7.1"
        val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object MvRx {
        private const val version = "1.0.1"
        val mvrx = "com.airbnb.android:mvrx:$version"
    }


//    object Test {
//        val junit = "junit:junit:4.12"
//        val core = "androidx.test:core:1.0.0"
//        val mockito = "org.mockito:mockito-core:1.10.19"
//        val robolectric = "org.robolectric:robolectric:4.3-alpha-2"
//        val arch_core = "androidx.arch.core:core-testing:2.0.1"
//
//        object Espresso {
//            val core = "androidx.test.espresso:espresso-core:3.1.0"
//        }
//
//    }
//
//
//    object ATSL {
//        private const val version = "1.1.0-alpha4"
//        val runner = "androidx.test:runner:$version"
//        val rules = "androidx.test:rules:$version"
//    }


}