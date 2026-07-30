## 🧩 Gradle Build Script (build.gradle)
## ⚙️ Gradle Build Configuration
```
plugins {
id 'java'
id 'io.qameta.allure' version '2.11.2'
}

tasks.withType(JavaCompile) {
options.encoding = 'UTF-8'
}

group = 'com.phonebook'
version = '1.0-SNAPSHOT'

repositories {
mavenCentral()
}

allure {
version = '2.24.0'
autoconfigure = true
aspectjweaver = true
}

tasks.register('cleanAllureReport') {
doLast {
delete "$buildDir/reports/allure-report"
}
}

tasks.named('allureReport') {
dependsOn 'cleanAllureReport'
}

configurations.all {
resolutionStrategy {
failOnVersionConflict()
force 'org.apache.commons:commons-lang3:3.14.0'
force 'commons-codec:commons-codec:1.15'
force 'org.seleniumhq.selenium:selenium-api:4.25.0'
force 'org.seleniumhq.selenium:selenium-remote-driver:4.25.0'
force 'org.seleniumhq.selenium:selenium-support:4.25.0'
force 'org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.10'
force 'org.slf4j:slf4j-api:2.0.12'
force 'org.jetbrains:annotations:17.0.0'
force 'org.testng:testng:7.11.0'
}
}

dependencies {
    // ===== TestNG + Allure =====
    implementation 'org.testng:testng:7.11.0'
    testImplementation 'io.qameta.allure:allure-testng:2.24.0'
    testImplementation 'io.qameta.allure:allure-java-commons:2.24.0'

    // ===== Logging =====
    implementation 'org.slf4j:slf4j-api:2.0.12'
    implementation 'ch.qos.logback:logback-classic:1.4.11'

    // ===== Selenium =====
    implementation 'org.seleniumhq.selenium:selenium-java:4.25.0'

    // ===== REST Assured =====
    implementation 'io.rest-assured:rest-assured:5.4.0'
    implementation 'com.google.code.gson:gson:2.10.1'

    // ===== Mobile (Appium) =====
    implementation 'io.appium:java-client:9.2.2'

    // ===== HTTP Client =====
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'

    // ===== Utilities =====
    implementation 'net.datafaker:datafaker:2.5.2'
    implementation 'commons-io:commons-io:2.18.0'
    implementation 'commons-codec:commons-codec:1.15'

    // ===== Lombok =====
    compileOnly 'org.projectlombok:lombok:1.18.32'
    annotationProcessor 'org.projectlombok:lombok:1.18.32'
    testCompileOnly 'org.projectlombok:lombok:1.18.32'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.32'

    // ===== SQL + Docker =====
    implementation 'com.mysql:mysql-connector-j:9.7.0'
    testImplementation 'org.testcontainers:testcontainers:1.19.7'
    testImplementation 'org.testcontainers:mysql:1.19.7'
}

tasks.withType(Test).configureEach {
useTestNG()
jvmArgs = [
'--add-opens', 'java.base/java.lang=ALL-UNNAMED',
'--add-opens', 'java.base/java.util=ALL-UNNAMED',
'--add-opens', 'java.base/java.io=ALL-UNNAMED'
]
finalizedBy 'allureReport'
}

java {
toolchain {
languageVersion = JavaLanguageVersion.of(17)
}
}
```
## 💡 Notes
- Allure интегрирован для автоматического создания отчётов после тестов.
- TestNG используется как основной фреймворк для тестирования.
- Selenium, Appium, REST Assured — обеспечивают поддержку Web, Mobile и API‑тестов.
- Lombok упрощает работу с моделями и билдерами.
- Testcontainers позволяет запускать интеграционные тесты с MySQL в Docker.
- Кодировка UTF‑8 гарантирует корректную работу с русским и английским текстом.
- cleanAllureReport очищает старые отчёты перед генерацией новых.