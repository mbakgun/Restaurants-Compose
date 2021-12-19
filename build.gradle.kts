buildscript {

  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath(kotlin("gradle-plugin", version = Versions.kotlin))
    classpath(Deps.gradle)
    classpath(Deps.kotlinPlugin)
    classpath(Deps.hiltPlugin)
  }
}

tasks.register("clean").configure {
  delete(rootProject.buildDir)
}
