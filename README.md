# GGWP_DemoWebShop-Automation
# Selenium Test Automation for Demo Web Shop

## Overview
This project automates the registration, login, and product search functionalities for the Demo Web Shop application using Selenium with TestNG in Java.

## Technologies Used
- Java
- Selenium WebDriver
- TestNG
- Gradle
- ChromeDriver

## Test Cases Implemented
1. **Register a New User**
   - Fills out the registration form with a dynamically generated email.
   - Verifies successful registration.
   - Logs out after registration.

2. **Login with Valid Credentials**
   - Logs in using the registered email and password.
   - Asserts that login was successful.

3. **Login with Invalid Credentials**
   - Attempts to log in with incorrect credentials.
   - Validates error message.

4. **Search for an Existing Product**
   - Logs in and searches for a product.
   - Ensures that search results are displayed.

5. **Search Without Entering a Product**
   - Tries to search without any input.
   - Handles and verifies an alert.

## Prerequisites
Ensure the following are installed:
- Java (JDK 11 or later)
- Gradle
- Google Chrome
- ChromeDriver (compatible version with your Chrome browser)

## Setup and Execution
1. **Clone the Repository**
   ```sh
   git clone https://github.com/SharathRaikotySDET/GGWP_DemoWebShop-Automation.git
   cd GGWP_DemoWebShop-Automation
   ```

2. **Set Up Dependencies**
   - Ensure Gradle is installed.
   - Run:
     ```sh
     gradle clean build
     ```

3. **Execute Tests**
   - Run all tests:
     ```sh
     gradle test
     ```
   - Run a specific test:
     ```sh
     gradle test --tests TestCases.testCase01_RegisterUser
     ```

## Project Structure
```
GGWP_DemoWebShop-Automation/
│-- src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Wrappers.java (Utility functions for WebDriver interactions)
│   ├── test/
│   │   ├── java/
│   │   │   ├── TestCases.java (Test scripts)
│-- build.gradle
│-- README.md
```

## Wrappers Utility
- `click(WebElement element, ChromeDriver driver)`: Clicks on an element, ensuring visibility and scroll.
- `sendKeys(WebElement element, String text, ChromeDriver driver)`: Sends text input with visibility check.

## Logs & Debugging
- Logs are stored in `build/chromedriver.log`.

## Reporting
- TestNG reports can be found in `build/reports/tests/test/index.html` after execution.

## Author
**Sharath Raikoty** - *Automation Engineer*

## License
This project is for learning and personal use only.

