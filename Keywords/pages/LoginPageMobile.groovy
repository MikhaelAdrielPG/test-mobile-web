package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.nio.file.Files
import java.nio.file.Paths

import org.apache.http.Consts

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.CSVData
import com.kms.katalon.core.testdata.ExcelData
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.reader.ExcelFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonSlurper
import helpers.Constants as Const
import locators.LoginPageMobileLocator as Locator
import helpers.GlobalHelper as Helper

import internal.GlobalVariable

public class LoginPageMobile extends BasePage {

	@Keyword
	def public void login(String username, String password) {
		Mobile.setText(findByXPath(Locator.usernameXPath), username, 10)
		Mobile.setText(findByXPath(Locator.passwordXPath), password, 10)
		Mobile.tap(findByXPath(Locator.loginButtonXPath), 10)
	}

	@Keyword
	def public void verifyElementPresentByXPath(String xpath, int timeout) {
		boolean isPresent = Mobile.verifyElementExist(findByXPath(xpath), timeout)

		if (isPresent) {
			KeywordUtil.logInfo("Element found: " + xpath)
		} else {
			KeywordUtil.markFailed("Element not found: " + xpath)
		}
	}

	@Keyword
	def public void verifyLoginSuccess() {
		verifyElementPresentByXPath(Locator.dashboardXPath, 10)
	}

	@Keyword
	def public void checkoutXLS(String scenarioId, String testDataPath, String sheet) {
		try {
			ExcelData testData = ExcelFactory.getExcelDataWithDefaultSheet(testDataPath, sheet, true)

			int rowIndex = -1
			for (int i = 1; i <= testData.getRowNumbers(); i++) {
				if (testData.getValue("ScenarioId", i) == scenarioId) {
					rowIndex = i
					break
				}
			}

			if (rowIndex != -1) {
				String firstName = testData.getValue("FirstName", rowIndex)
				String lastName = testData.getValue("LastName", rowIndex)
				String postalCode = testData.getValue("PostalCode", rowIndex)

				KeywordUtil.logInfo("Performing checkout with data: ${scenarioId}, ${firstName}, ${lastName}, ${postalCode}")

				Mobile.tap(findByXPath(Locator.addToChart), 3)
				Mobile.tap(findByXPath(Locator.btnChart), 3)
				Mobile.tap(findByXPath(Locator.btnCheckout), 3)
				Mobile.setText(findByXPath(Locator.txtFirstName), firstName, 1)
				Mobile.setText(findByXPath(Locator.txtLastName), lastName, 1)
				Mobile.setText(findByXPath(Locator.txtZip), postalCode, 1)
				Mobile.hideKeyboard()
				Mobile.tap(findByXPath(Locator.btnContinue), 3)
				Mobile.scrollToText(Const.FINISH_BUTTON)
				Mobile.tap(findByXPath(Locator.btnFinish), 3)
			} else {
				KeywordUtil.markFailed("Scenario ID ${scenarioId} not found in test data.")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Test execution failed: " + e.message)
		}
	}

	@Keyword
	def public void checkoutCSV(String scenarioId, String data) {
		try {
			TestData testData = findTestData(data)

			if (testData == null) {
				KeywordUtil.markFailed("Test data CSV tidak ditemukan.")
				return
			}

			int rowIndex = findRowIndexCSV(testData, "ScenarioId", scenarioId)

			if (rowIndex != -1) {
				String firstName = testData.getValue("FirstName", rowIndex)
				String lastName = testData.getValue("LastName", rowIndex)
				String postalCode = testData.getValue("PostalCode", rowIndex)

				if (!firstName || !lastName || !postalCode) {
					KeywordUtil.markFailed("Data tidak lengkap untuk scenarioId: ${scenarioId}")
					return
				}

				KeywordUtil.logInfo("Melakukan checkout dengan data: ${scenarioId}, ${firstName}, ${lastName}, ${postalCode}")

				Mobile.tap(findByXPath(Locator.addToChart), 3)
				Mobile.tap(findByXPath(Locator.btnChart), 3)
				Mobile.tap(findByXPath(Locator.btnCheckout), 3)

				Mobile.waitForElementPresent(findByXPath(Locator.txtFirstName), 5)
				Mobile.setText(findByXPath(Locator.txtFirstName), firstName, 1)

				Mobile.waitForElementPresent(findByXPath(Locator.txtLastName), 5)
				Mobile.setText(findByXPath(Locator.txtLastName), lastName, 1)

				Mobile.waitForElementPresent(findByXPath(Locator.txtZip), 5)
				Mobile.setText(findByXPath(Locator.txtZip), postalCode, 1)

				Mobile.hideKeyboard()
				Mobile.tap(findByXPath(Locator.btnContinue), 3)

				Mobile.scrollToText(Const.FINISH_BUTTON)
				Mobile.tap(findByXPath(Locator.btnFinish), 3)

				KeywordUtil.logInfo("Checkout selesai untuk Scenario ID: ${scenarioId}")
			} else {
				KeywordUtil.markFailed("Scenario ID ${scenarioId} tidak ditemukan dalam file CSV.")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Eksekusi test gagal: " + e.message)
		}
	}

	@Keyword
	def public int findRowIndexCSV(TestData testData, String columnName, String searchValue) {
		for (int i = 1; i <= testData.getRowNumbers(); i++) {
			if (testData.getValue(columnName, i)?.equalsIgnoreCase(searchValue)) {
				return i
			}
		}
		return -1
	}

	@Keyword
	def public void checkoutJSON(String scenarioId, String pathJSON) {
		try {
			def jsonContent = new String(Files.readAllBytes(Paths.get(pathJSON)))
			def jsonSlurper = new JsonSlurper().parseText(jsonContent)

			def scenario = jsonSlurper.find { it.ScenarioId == scenarioId }

			if (scenario) {
				String firstName = scenario.FirstName
				String lastName = scenario.LastName
				String postalCode = scenario.PostalCode

				KeywordUtil.logInfo("Performing checkout with data: ${scenarioId}, ${firstName}, ${lastName}, ${postalCode}")

				Mobile.tap(findByXPath(Locator.addToChart), 3)
				Mobile.tap(findByXPath(Locator.btnChart), 3)
				Mobile.tap(findByXPath(Locator.btnCheckout), 3)
				Mobile.setText(findByXPath(Locator.txtFirstName), firstName, 1)
				Mobile.setText(findByXPath(Locator.txtLastName), lastName, 1)
				Mobile.setText(findByXPath(Locator.txtZip), postalCode, 1)
				Mobile.hideKeyboard()
				Mobile.tap(findByXPath(Locator.btnContinue), 3)
				Mobile.scrollToText(Const.FINISH_BUTTON)
				Mobile.tap(findByXPath(Locator.btnFinish), 3)
			} else {
				KeywordUtil.markFailed("Scenario ID ${scenarioId} not found in JSON data.")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Test execution failed: " + e.message)
		}
	}
}