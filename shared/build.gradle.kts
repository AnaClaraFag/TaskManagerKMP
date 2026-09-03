import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlixKover)
}

detekt {
    toolVersion = "1.23.7"
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    baseline = file("$projectDir/detekt-baseline.xml")
    allRules = false
}

sqldelight {
    databases {
        register("TaskDatabase") {
            packageName.set("com.example.taskmanagerkmp.shared.db")
        }
    }
}

tasks.register("detektAll") {
    group = "verification"
    description = "Roda Detekt em todos os source sets KMP"
    dependsOn(
        "detektMetadataCommonMain",
        "detektAndroidMain",
        "detektMetadataIosMain"
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        sarif.required.set(false)
        md.required.set(false)
    }
}

kotlin {

    cocoapods {
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
            implementation(libs.koin.android)

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

            // Koin core
            implementation(libs.koin.core)
            // Koin para Compose Multiplatform
            implementation(libs.koin.compose)
        }
        iosMain.dependencies {
            implementation(libs.native.driver)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    detektPlugins(libs.detekt.formatting)
}