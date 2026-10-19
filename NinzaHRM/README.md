# Ninza-HRM Selenium Automation Starter

Java + Selenium WebDriver + TestNG + Maven + Page Object Model (POM). Package and helper naming follow the uploaded `ecom.Bitm.SwagLabs` project where applicable.

## Covered modules
- Login Page
- Home Page
- Projects
- Settings

## Requirements
- JDK 21 (or update `maven.compiler.release` in `pom.xml` to your installed JDK)
- Maven 3.9+
- Google Chrome
- Network access to the test application

## Setup
1. Import this folder into Eclipse as an **Existing Maven Project**.
2. Open `src/main/resources/DD/config1.properties`.
3. Keep the provided URL or update it if your test environment changes.
4. Replace `YOUR_TEST_USERNAME` and `YOUR_TEST_PASSWORD` with authorized test credentials. Do not commit real credentials to source control.
5. Right-click the project > Maven > Update Project.

## Current starter scope
This first stage includes Maven configuration, the properties utility, WebDriver utility, BaseClass01, and TestNG group suite files. Page objects and test classes are intentionally added after inspecting the live application's DOM so selectors are not guessed.

## Run a suite
From a terminal in this project directory:

```bash
mvn test -DsuiteXmlFile=SmokeGroup.xml
mvn test -DsuiteXmlFile=Functionalgroup.xml
mvn test -DsuiteXmlFile=Integrationgroup.xml
mvn test -DsuiteXmlFile=NegativeTestCase.xml
```

The suites will report no tests until the page-object and test classes are added in the next stage.
