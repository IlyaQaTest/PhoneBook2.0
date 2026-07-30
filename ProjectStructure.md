### 📁 PhoneBook 2.0 — Project Structure

### 🧩 Root Level
```
📁 PhoneBook2.0/
├── .github/
│   └── workflows/
│       └── smoketests.yml
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   ├── test/
│   └── resources/
├── README.md
├── HELPER.md
├── BUILD.md
├── build.gradle
├── settings.gradle
├── .gitignore
```
### 🧠 `src/main/java/com.phonebook/`

### 🔹 API — REST clients and DTOs
```
api/
├── client/
│   ├── AuthenticationController.java
│   ├── BaseApi.java
│   ├── ContactController.java
│   └── ILogin.java
└── dto/
    └── TokenDto.java
```
### ⚙️ Core Components
```
core/
├── config/
│   └── PropertiesReader.java
├── helpers/
│   ├── CrashHandler.java
│   ├── CrashHandlerPrtSC.java
│   └── ScreenshotUtils.java
└── enums/
└── Direction.java
```
### 🧩 Model Layer
```
model/
├── Contact.java
├── ContactsList.java
├── ErrorMessage.java
├── ResponseMessage.java
├── User.java
└── factory/
    ├── ContactFactory.java
    ├── UserBuilder.java
    └── UserFactory.java
```
### 🗄️ DatabaseModule(mydb)
```
mydb/
├── config/
│   └── ApiConfigBD.java
├── model/
│   └── ContactDb.java
├── helpers/
│   └── DBHelperMy.java
├── client/
│   └── ContactDbController.java
├── ApiHelperBD.java
├── UiHelperBD.java
└── MobileHelperBD.java
```
### 📱 Mobile Automation
```
mobile/
├── config/
│   └── AppiumConfig.java
├── helpers/
│   └── SwipeUtils.java
└── screens/
    ├── AddNewContactScreen.java
    ├── BaseScreen.java
    ├── ContactListScreen.java
    ├── EditContactScreen.java
    ├── ErrorScreen.java
    ├── LoginRegistrationScreen.java
    ├── SplashScreen.java
    └── UpdateContactScreen.java
```
### 💻 Web UI Automation
```
ui/
├── config/
│   └── BrowserConfig.java (optional)
├── manager/
│   └── AppManager.java
├── pages/
│   ├── AboutPage.java
│   ├── AddPage.java
│   ├── BasePage.java
│   ├── ContactPage.java
│   ├── HomePage.java
│   └── LoginPage.java
└── utils/
    ├── HeaderMenuItem.java
    └── WDListener.java
```

### 🐳 Docker Support
```
docker/
└── Dockerfile / ComposeConfig.java (optional)
```
### 📂 src/main/resources/
```
resources/
├── data_csv/
│   ├── data_contacts.csv
│   ├── dp_empty_field.csv
│   └── dp_wrong_phone.csv
└── logback.xml
```
### 🧪 src/test/java/com.phonebook.tests/`

### 🔹 API Tests
```
api/
├── AddContactApiTests.java
├── DeleteContactApiTests.java
├── EditContactApiTests.java
├── GetAllContactsApiTests.java
├── LoginApiTests.java
└── RegistrationApiTests.java
```
### 💻 UI Tests
```
ui/
├── AddNewContactTests.java
├── DeleteContactTests.java
├── EditContactTests.java
├── LoginTests.java
└── RegistrationTests.java
```
### 📱 Mobile Tests
```
mobile/
├── AddNewContactTests.java
├── DeleteContactTests.java
├── EditContactTests.java
├── LoginTests.java
├── LogoutTests.java
├── RegistrationTests.java
├── SplashScreenTests.java
├── TestBase.java
└── UpdateContactTests.java
```
### 🔗 Integration Tests
```
integration/
└── ApiUiMobileIntegrationTests.java
```
### 🧰 Utility Tests
```
utils_test/
├── data_providers/
│   └── ContactDataProvider.java
├── retry/
│   └── RetryAnalyser.java
└── listeners/
├── AllureTestListener.java
└── TestNGListener.java
```
## 📦 `src/test/resources/`
```
resources/
├── properties/
│   ├── base.properties
│   ├── pixel.properties
│   └── testng.xml
├── commands.txt
├── logback.xml
├── negative_tests.xml
├── smoke_contact_tests.xml
└── smoke_tests.xml
```
## 🧰 Root Files
- .gitignore
- build.gradle
- gradlew
- gradlew.bat
- HELPER.md
- README.md
- settings.gradle
