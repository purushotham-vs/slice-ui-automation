# e2e-slice-app-automation

Slice E2E app automation

Technologies/Tools used in building the framework
=================================================

- IntelliJ - IDE
- Appium - Mobile Automation library
- Maven - Build automation tool
- Java - Programming language
- Cucumber - BDD
- Gherkin - DSL
- Log4J - Logging framework
- JSON - Test Data
- GitHub - Version control
- GOCD - CI/CD
- Reporting - Cucumber default

Command to run the test
-------------------------

```
mvn test
```

Run test with tags
-------------------

```
 mvn test -Dcucumber.options="--tags <tag name>"
```

Note : Require AVD manager to open any android device in order to check the app flow on emulator

Note : In order to run in Local ,You Need not to be install and start the appium server.(Not mandatory)