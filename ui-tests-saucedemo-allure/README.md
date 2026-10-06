# Swag Labs UI tests + Allure + cross-browser (practical work 14-16)

Maven + Selenium 4.22 + TestNG 7.10 + Allure 2.25, Page Object pattern.
Extends the saucedemo.com project: same 11 tests, now with Allure reporting and Chrome/Firefox/Edge runs.

## Structure
```
src/test/java/pages/   BasePage + Login/Inventory/Cart/Checkout pages (@Step on business actions)
src/test/java/tests/   BaseTest (driver lifecycle, failure attachments) + Login/Cart/Checkout/Sorting tests
                       + FailureDemoTest (intentional failure, only for the report screenshot)
src/test/java/utils/   Config, DriverFactory (chrome|firefox|edge), AllureAttachments, EnvironmentWriter
src/test/resources/    testng.xml, testng-crossbrowser.xml, testng-failure-demo.xml, allure.properties, categories.json
```

## Run
```bash
mvn clean test                              # Chrome, visible
mvn clean test -Dbrowser=firefox            # one browser via system property
mvn clean test -Dbrowser=edge -Dheadless=true
mvn clean test -Pcrossbrowser               # Chrome + Firefox + Edge in parallel (3 threads)
mvn clean test -Pcrossbrowser -Pheadless    # same, headless (CI)
mvn clean test -Pfailure-demo               # one intentional failure -> failed card with screenshot in Allure
```
Browser resolution order in `BaseTest`: `testng.xml` parameter -> `-Dbrowser` -> `chrome`.
(That is why `-Dbrowser` works with `testng.xml`, which has no browser parameter, and is ignored by `testng-crossbrowser.xml`, which sets one per `<test>`.)

## Allure
```bash
mvn allure:report     # HTML -> target/site/allure-maven/index.html
mvn allure:serve      # temporary local server (Ctrl+C to stop)
```
- Raw results: `target/allure-results` (JSON + attachments), produced by the `allure-testng` adapter.
- `@Step` works through AspectJ load-time weaving (`aspectjweaver` javaagent in surefire `argLine`).
- Labels: `@Epic`, `@Feature`, `@Story`, `@Severity`, `@Owner` (see tests). Replace "Фамилия Имя" with your name.
- Attachments on failure: screenshot, page source, current URL (`BaseTest.tearDown`).
- Environment widget: `environment.properties` is generated after the suite (`EnvironmentWriter`): OS, JDK, Selenium, TestNG, real browser versions, driver manager.
- `categories.json` groups failures (assertion / element not found-timeout / browser-driver).

## Screenshots needed for the report
Green `mvn test`, `target/surefire-reports`, Allure: Dashboard, Suites/Behaviors, failed test card (use `-Pfailure-demo`), Environment.

## Notes
- Needs Chrome, Firefox and Edge installed for the cross-browser run. Drivers are resolved automatically by Selenium Manager (no WebDriverManager).
- Explicit waits only; locators are `By.id` and `[data-test=...]`; the driver is always closed in `@AfterMethod(alwaysRun = true)`.
- Parallelism is `parallel="tests"`: each `<test>` creates its own class instances, so each browser has its own driver.

## Git
Branch: `feature/<Группа>_<Фамилия>_saucedemo_allure`
```bash
git init && git add . && git commit -m "chore: init project with Allure and cross-browser setup"
git checkout -b feature/<Группа>_<Фамилия>_saucedemo_allure
# atomic commits, e.g.: feat: add Allure steps and labels / feat: cross-browser DriverFactory / test: add failure demo
git remote add origin https://github.com/<аккаунт>/ui-tests-saucedemo-allure-<Фамилия>.git
git push -u origin feature/<Группа>_<Фамилия>_saucedemo_allure
```
Open a Pull Request and add the reviewer. Put the branch URL, PR link and `git rev-parse HEAD` in the report.
