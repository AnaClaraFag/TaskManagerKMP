plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.kotlinCocoapods) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kotlixKover) apply false
}

val localProperties = java.util.Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) load(file.inputStream())
}

sonarqube {
    properties {
        property("sonar.projectKey",  "ana-fagundes_TaskManagerKMP")
        property("sonar.projectName", "TaskManager KMP")
        property("sonar.organization", localProperties["sonar.organization"] ?: "")
        property("sonar.host.url",    "https://sonarcloud.io")
        property("sonar.token",       localProperties["sonar.token"] ?: "")

        property("sonar.gradle.skipCompile", "true")
        property("sonar.language",       "kotlin")
        property("sonar.sourceEncoding", "UTF-8")
    }
}

// Configure the analysis on the :shared module itself so that source/test
// sets are defined relative to the module and override the plugin's KMP
// source-set auto-detection (which otherwise indexes commonMain as both
// main and test, causing "can't be indexed twice").
project(":shared") {
    sonarqube {
        properties {
            property("sonar.sources", "src/commonMain/kotlin")
            property("sonar.tests",   "src/commonTest/kotlin")

            // Relatório do Detekt
            property("sonar.kotlin.detekt.reportPaths",
                "build/reports/detekt/metadataCommonMain.xml")

            // Cobertura (Kover)
            property("sonar.coverage.jacoco.xmlReportPaths",
                "build/reports/kover/report.xml")

            // Exclusões
            property("sonar.exclusions", listOf(
                "**/build/**",
                "**/generated/**",
                "**/*.sq",
                "**/db/**",
                "**/di/**"
            ).joinToString(","))
        }
    }
}