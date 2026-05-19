# 📱 PhoneBook 2.0 — Test Automation Framework

PhoneBook 2.0 is a multi‑layer automated testing framework designed to validate the functionality of the PhoneBook application across **API**, **Web UI**, and **Mobile** platforms.  
The project follows clean architecture principles and demonstrates professional automation practices suitable for real‑world QA engineering.

---

## 👨‍💻 About the Author

My name is **Ilya**, and I am a **Junior QA Automation Engineer** based in **Israel**.  
This project reflects my practical experience in building scalable automation frameworks and improving my skills in:

- API automation (RestAssured, OkHttp)
- Web UI automation (Selenium WebDriver)
- Mobile automation (Appium)
- Test architecture and clean code principles
- CI/CD and reporting (Allure, Gradle, GitHub Actions)

I am passionate about software quality, continuous learning, and building real automation solutions.

---

## 🚀 Project Goals

- Provide full automated coverage for the PhoneBook application
- Build a clean, maintainable, and scalable automation framework
- Support API, Web UI, and Mobile testing in a unified structure
- Enable integration tests combining all layers
- Deliver clear and informative Allure reports
- Prepare the project for CI/CD pipelines and containerized execution

---

## 🧱 Project Structure
```
src/
├── main/
│    ├── java/
│    │    └── com.phonebook/
│    │         ├── api/                     ← API clients + DTO models
│    │         ├── mobile/                  ← Mobile automation (Appium)
│    │         ├── ui/                      ← Web UI automation (Selenium)
│    │         ├── core/                    ← Shared utilities and configs
│    │         ├── model/                   ← Business models + factories
│    │         ├── db/                      ← Database utilities
│    │         └── docker/                  ← Docker configs
│    │
│    └── resources/
│         ├── data_csv/                     ← Test data
│         └── logback.xml                   ← Logging config
│
└── test/
├── java/
│    └── com.phonebook.tests/
│         ├── api/                     ← API test suite
│         ├── ui/                      ← Web UI tests
│         ├── mobile/                  ← Mobile tests
│         ├── integration/             ← Cross‑layer tests
│         └── utils_test/              ← Data providers & listeners
│
└── resources/
└── properties/                   ← Environment configs
```

## 🧪 API Test Suite

The API layer is fully implemented and includes:

- Registration tests
- Login tests
- Add Contact tests
- Edit Contact tests
- Delete Contact tests
- Get All Contacts tests

Features:

- RestAssured + OkHttp hybrid approach
- DTO‑based request/response models
- Allure annotations (`@Issue`, `@Description`)
- Soft assertions for detailed validation
- Token‑based authentication
- Clean helper methods and reusable controllers

---

## 🧰 Technology Stack

### **Languages & Frameworks**
- Java 17
- TestNG
- RestAssured
- Selenium WebDriver *(UI — upcoming)*
- Appium *(Mobile — upcoming)*

### **Reporting**
- Allure Report
- Logback

### **Build & CI**
- Gradle
- Docker
- GitHub Actions *(planned)*

---

## 📦 Installation & Setup

Clone the repository:

```bash
git clone https://github.com/IlyaQaTest/PhoneBook2.0.git
cd PhoneBook2.0
Run tests:
```
---
```bash
gradle clean test
Generate Allure report:
```
---
```bash
gradle allureReport
```
---
## 🔜 **Upcoming Modules**
- Web UI Automation
- WebDriver configuration
- Page Object Model
- UI smoke tests
- Cross‑browser support

## Mobile Automation
- Appium configuration
- Screen Object Model
- Android emulator support
- End‑to‑end flows

## Integration Tests
- API + UI + Mobile combined scenarios
- Data synchronization validation

## 📄 License
**MIT License**

## ⭐ Support
**If you like this project, feel free to star the repository — it helps visibility
and supports my growth as a QA Automation Engineer.**