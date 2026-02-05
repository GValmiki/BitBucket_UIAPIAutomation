# 🚀 BitBucket_UIAPIAutomation

A hybrid **UI + API automation framework** built using **Java, Selenium, TestNG**, focused on automating **Bitbucket workflows** with a clean, reusable, and data-driven design.

---

## 📌 Overview

This project provides a **single automation framework** for both **UI and API testing**.  
All test data is driven from **Excel**, avoiding hardcoded values and enabling easy scalability.

---

## 🛠 Tech Stack

- **Language:** Java (JDK 17)
- **UI Automation:** Selenium WebDriver
- **API Automation:** Java `HttpURLConnection`
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Reporting:** Extent Reports
- **Logging:** Log4j2
- **Data Driven:** Apache POI (Excel)
- **Version Control:** Git & GitHub

---

## 📂 Project Structure
Automate_bitbucket
│
├── src/main/java
│ ├── pages
│ │ ├── ProjectCreationPage.java
│ │ └── RepoCreatioPpage.java
│ │
│ ├── utils
│ │ ├── ConfigReader.java
│ │ ├── Constants.java
│ │ ├── RetryAnalyzer.java
│ │ ├── ExtentManager.java
│ │ └── UIUtility.java
│── src/test/resources
│ ├── log4j2.xml
│ └── config.properties

├── src/test/java
│ │ └── ProjectCreationTest.java
| | └───RepoCreationTest
│ │
│ └── BaseAPI.java
│ └── BaseTest.java
│
├
│
└── pom.xml
