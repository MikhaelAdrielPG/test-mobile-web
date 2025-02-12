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
import helpers.GlobalHelper as Helper
import helpers.Constants as Const
import locators.LoginPageWebLocator as Locator


import internal.GlobalVariable

public class LoginPageWeb {
	@Keyword
	public void login(String username, String password) {
		WebUI.setText(Helper.findByXPath(Locator.usernameInputXPath), username)
		WebUI.setEncryptedText(Helper.findByXPath(Locator.passwordInputXPath), password)
		WebUI.click(Helper.findByXPath(Locator.loginButtonXPath))
	}

	@Keyword
	public void verifyElementPresentByXPath(String xpath, int timeout) {
		boolean isPresent = WebUI.verifyElementPresent(Helper.findByXPath(xpath), timeout)

		if (isPresent) {
			KeywordUtil.logInfo("Element found: " + xpath)
		} else {
			KeywordUtil.markFailed("Element not found: " + xpath)
		}
	}

	@Keyword
	public void verifyLoginSuccess() {
		verifyElementPresentByXPath(Locator.dashboardXPath, 10)
	}

	@Keyword
	public void navigateToAdminMenu() {
		WebUI.waitForElementPresent(Helper.findByXPath(Locator.adminMenu), 10)
		WebUI.click(Helper.findByXPath(Locator.adminMenu))
	}

	@Keyword
	public void fillAdminForm(String scenarioId) {
		try {
			def testDataPath = "Data Files/xls/data.xlsx"
			ExcelData testData = ExcelFactory.getExcelDataWithDefaultSheet(testDataPath, "Sheet1", true)

			int rowIndex = -1
			for (int i = 1; i <= testData.getRowNumbers(); i++) {
				if (testData.getValue("ScenarioId", i) == scenarioId) {
					rowIndex = i
					break
				}
			}

			if (rowIndex != -1) {
				String username = testData.getValue("Username", rowIndex)
				String userRole = testData.getValue("UserRole", rowIndex)
				String employeeName = testData.getValue("EmployeeName", rowIndex)
				String status = testData.getValue("Status", rowIndex)

				WebUI.comment("Filling form with data: ${scenarioId}, ${username}, ${userRole}, ${employeeName}, ${status}")

				WebUI.setText(Helper.findByXPath(Locator.usernameField), username)

				selectDropdownOptionByText(Locator.userRoleDropdown, userRole)

				WebUI.setText(Helper.findByXPath(Locator.employeeNameField), employeeName)

				selectDropdownOptionByText(Locator.statusDropdown, status)

				WebUI.click(Helper.findByXPath(Locator.searchButton))

				WebUI.comment("Data from ${scenarioId} successfully filled into the form.")
			} else {
				WebUI.comment("Scenario ID ${scenarioId} not found in test data.")
			}
		} catch (Exception e) {
			WebUI.comment("Test execution failed: " + e.message)
		}
	}

	@Keyword
	public void fillAdminFormCSV(String scenarioId) {
		try {
			TestData testData = findTestData("csv/data")

			if (testData == null) {
				WebUI.comment("Test data CSV not found.")
				return
			}


			int rowIndex = findRowIndexCSV(testData, "ScenarioId", scenarioId)

			if (rowIndex != -1) {
				String username = testData.getValue("Username", rowIndex) ?: ""
				String userRole = testData.getValue("UserRole", rowIndex) ?: ""
				String employeeName = testData.getValue("EmployeeName", rowIndex) ?: ""
				String status = testData.getValue("Status", rowIndex) ?: ""

				if (username.isEmpty() || userRole.isEmpty() || employeeName.isEmpty() || status.isEmpty()) {
					WebUI.comment("Data not completed for scenarioId: ${scenarioId}")
					return
				}

				WebUI.comment("Fill form with data: ${scenarioId}, ${username}, ${userRole}, ${employeeName}, ${status}")

				WebUI.waitForElementPresent(Helper.findByXPath(Locator.usernameField), 10, FailureHandling.OPTIONAL)
				WebUI.setText(Helper.findByXPath(Locator.usernameField), username)

				selectDropdownOptionByText(Locator.userRoleDropdown, userRole)

				WebUI.waitForElementPresent(Helper.findByXPath(Locator.employeeNameField), 10, FailureHandling.OPTIONAL)
				WebUI.setText(Helper.findByXPath(Locator.employeeNameField), employeeName)

				selectDropdownOptionByText(Locator.statusDropdown, status)

				WebUI.click(Helper.findByXPath(Locator.searchButton))

				WebUI.comment("Data from ${scenarioId} succeed input to the form.")
			} else {
				WebUI.comment("Scenario ID ${scenarioId} not found in CSV file.")
			}
		} catch (Exception e) {
			WebUI.comment("text execution failed: " + e.message)
		}
	}

	@Keyword
	public int findRowIndexCSV(TestData testData, String columnName, String searchValue) {
		for (int i = 1; i <= testData.getRowNumbers(); i++) {
			String cellValue = testData.getValue(columnName, i) ?: ""
			if (cellValue.equals(searchValue)) {
				return i
			}
		}
		return -1
	}

	@Keyword
	public void fillAdminFormJSON(String scenarioId) {
		try {
			def jsonFilePath = "Data Files/json/data.json"

			File file = new File(jsonFilePath)
			if (!file.exists()) {
				WebUI.comment("File JSON tidak ditemukan: ${jsonFilePath}")
				return
			}

			def jsonContent = new JsonSlurper().parseText(file.text)

			def scenarioData = jsonContent.find { it.ScenarioId == scenarioId }

			if (scenarioData) {
				String username = scenarioData.Username ?: ""
				String userRole = scenarioData.UserRole ?: ""
				String employeeName = scenarioData.EmployeeName ?: ""
				String status = scenarioData.Status ?: ""

				if (username.isEmpty() || userRole.isEmpty() || employeeName.isEmpty() || status.isEmpty()) {
					WebUI.comment("Data tidak lengkap untuk Scenario ID: ${scenarioId}")
					return
				}

				WebUI.comment("Mengisi formulir dengan data: ${scenarioId}, ${username}, ${userRole}, ${employeeName}, ${status}")

				WebUI.waitForElementPresent(Helper.findByXPath(Locator.usernameField), 10, FailureHandling.OPTIONAL)
				WebUI.setText(Helper.findByXPath(Locator.usernameField), username)

				selectDropdownOptionByText(Locator.userRoleDropdown, userRole)

				WebUI.waitForElementPresent(Helper.findByXPath(Locator.employeeNameField), 10, FailureHandling.OPTIONAL)
				WebUI.setText(Helper.findByXPath(Locator.employeeNameField), employeeName)

				selectDropdownOptionByText(Locator.statusDropdown, status)

				WebUI.click(Helper.findByXPath(Locator.searchButton))

				WebUI.comment("Data dari Scenario ID ${scenarioId} berhasil dimasukkan ke dalam formulir.")
			} else {
				WebUI.comment("Scenario ID ${scenarioId} tidak ditemukan dalam file JSON.")
			}
		} catch (Exception e) {
			WebUI.comment("Eksekusi gagal: " + e.message)
		}
	}

	private void selectDropdownOptionByText(String dropdownXPath, String value) {
		WebUI.waitForElementPresent(Helper.findByXPath(dropdownXPath), 10)
		WebUI.click(Helper.findByXPath(dropdownXPath))

		String optionXPath = "//div[@class='oxd-select-option' and span[text()='${value}']]";

		WebUI.waitForElementPresent(Helper.findByXPath(optionXPath), 10)
		WebUI.click(Helper.findByXPath(optionXPath))
	}
}