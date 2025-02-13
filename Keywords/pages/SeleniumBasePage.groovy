package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import locators.MandiriClickLocator
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable

public class SeleniumBasePage {

	public static WebDriver driver

	@Keyword
	def public void initDriver() {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\Katalon Studio\\test\\Drivers\\chromedriver-win64\\chromedriver.exe")
			ChromeOptions options = new ChromeOptions()
			options.setExperimentalOption("androidPackage", "com.android.chrome")
			driver = new ChromeDriver(options)
			KeywordUtil.logInfo("ChromeDriver init success.")
		} catch (Exception e) {
			KeywordUtil.markFailed("Init driver failed: " + e.getMessage())
		}
	}

	@Keyword
	def public void closeDriver() {
		if(driver != null) {
			driver.quit()
			driver = null
			KeywordUtil.logInfo("ChromeDriver closed.")
		}
	}

	@Keyword
	def protected By findByXPath(String xpath) {
		if (!xpath?.trim()) {
			KeywordUtil.logInfo("XPath not valid: cannot be null or empty")
			return null
		}

		return By.xpath(xpath)
	}
}
