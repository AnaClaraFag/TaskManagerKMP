import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinCocoapods)
}

sqldelight {
    databases {
        register("TaskDatabase") {
            packageName.set("com.example.taskmanagerkmp.shared.db")
        }
    }
}

kotlin {

    cocoapods{
        version = "1.0"
        summary = "TaskManager KMP Shared Module"
        homepage = "https://github.com/AnaClaraFag/taskmanager"
        ios.deploymentTarget = "16.0"

        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    androidLibrary {
       namespace = "com.example.taskmanagerkmp.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.android.driver)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.navigation)
            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)
            // Coroutines (StateFlow para ViewModel)
            implementation(libs.kotlinx.coroutines.core)
            // SQLDelight (multiplatform local data)
            implementation(libs.runtime)
            implementation(libs.coroutines.extensions1)

            // DateTime (para campos de data na Task)
            implementation(libs.kotlinx.datetime)

            // Serialization (útil para exportar dados nas métricas)
            implementation(libs.kotlinx.serialization.json)
        }
        iosMain.dependencies {
            implementation(libs.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}