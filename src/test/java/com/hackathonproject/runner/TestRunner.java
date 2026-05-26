package com.hackathonproject.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.hackathonproject.base.BaseTest;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.hackathonproject.steps",
                "com.hackathonproject.base",
                "com.hackathonproject.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {

    private static final ThreadLocal<String> browserThreadLocal = new ThreadLocal<>();

    // Keep static for backward compatibility with ScreenshotUtil and BaseTest
    public static String browserName = "chrome";

    public static String getBrowserName() {
        return browserThreadLocal.get() != null ? browserThreadLocal.get() : browserName;
    }

    @BeforeClass
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        browserThreadLocal.set(browser);
        browserName = browser;
    }

    @AfterClass
    public void closeBrowser() {
        BaseTest.quitDriver();
        browserThreadLocal.remove();
    }
}