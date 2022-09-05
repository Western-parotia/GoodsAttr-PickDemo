plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.jvm") version "1.3.72"

}

repositories {
    mavenCentral()
    google()
    jcenter()
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation("com.android.tools.build:gradle:4.1.0")
    implementation(localGroovy())
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
