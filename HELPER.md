# 🧠 Helper / Quick Reference

## ⚙️ Gradle Commands

| Команда                                                       | Назначение                                   |
|---------------------------------------------------------------|----------------------------------------------|
| `.\gradlew clean smoke_tests`                                 | Очистка и запуск smoke‑тестов                |
| `.\gradlew clean smoke_contact_tests`                         | Очистка и Smoke‑тесты для контактов          |
| `.\gradlew clean negative_tests`                              | Очистка и Негативные сценарии                |
| `.\gradlew clean different_tests -Dsuite=smoke_contact_tests` | Очистка и Кастомный запуск с указанием suite |
| `.\gradlew clean browser_tests -Pbrowser=firefox`             | Очистка и Запуск тестов в Firefox            |
| `.\gradlew clean browser_tests -Pbrowser=edge`                | Очистка и Запуск тестов в Edge               |

---

## 💡 Notes
- `clean` удаляет предыдущие артефакты сборки перед запуском.
- Gradle Wrapper (`gradlew`) позволяет запускать тесты без установки Gradle.
- **После выполнения тестов:**
- Логи сохраняются в `logs/`
- Скриншоты ошибок — в `screenshots/`
- **Для CI/CD используется** `.github/workflows/smoketests.yml`.

---

## 🖥️ Запуск через терминал Windows

cd C:\AutoProjects\QA_50_32_PhoneBook_Web_Api
.\gradlew clean smoke_tests
(Запуск задачи без открытия IDE)

## 🧪 Checking the class work
## 🔍 CrashHandlerPrtSC.java
- java method
```
@Test(description = "Check CrashHandlerPrtSC screenshot creation")
public void verifyCrashHandlerPrtSCWorks() {
    CrashHandlerPrtSC.captureDesktopScreenshot("test_desktop_screenshot");
    File screenshot = new File("screenshots/test_desktop_screenshot_desktop.png");
    Assert.assertTrue(screenshot.exists(), "Desktop screenshot was not created!");
}
```
## 📱 Mobile ContactListScreen.java
- java method
```
public void refresh() {
    logger.info("Refreshing contact list (pull-to-refresh)");
    swipeScreen(driver, Direction.DOWN);
    try {
        Thread.sleep(1000);
    } catch (InterruptedException ignored) {
        logger.warn("Refresh wait interrupted");
    }
}
```