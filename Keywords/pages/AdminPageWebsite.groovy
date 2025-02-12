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
import com.kms.katalon.core.testdata.ExcelData
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testdata.reader.ExcelFactory
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import helpers.GlobalHelper as Helper

import internal.GlobalVariable


public class AdminPageWebsite {
	private String adminMenu = "//*[@id='app']/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a"
	private String usernameField = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input"
	private String userRoleDropdown = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/div/div"
	private String employeeNameField = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[3]/div/div[2]/div/div/input"
	private String statusDropdown = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[4]/div/div[2]/div/div"
	private String searchButton = "//*[@id='app']/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]"

	@Keyword
	public void navigateToAdminMenu() {
		WebUI.waitForElementPresent(Helper.findByXPath(adminMenu), 10)
		WebUI.click(Helper.findByXPath(adminMenu))
	}

	@Keyword
	public void fillAdminForm(String scenarioId) {
		try {
			def testDataPath = "Data Files/xls/admin.xlsx"
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

				WebUI.setText(Helper.findByXPath(usernameField), username)

				selectDropdownOptionByText(userRoleDropdown, userRole)

				WebUI.setText(Helper.findByXPath(employeeNameField), employeeName)

				selectDropdownOptionByText(statusDropdown, status)

				WebUI.click(Helper.findByXPath(searchButton))

				WebUI.comment("Data from ${scenarioId} successfully filled into the form.")
			} else {
				WebUI.comment("Scenario ID ${scenarioId} not found in test data.")
			}
		} catch (Exception e) {
			WebUI.comment("Test execution failed: " + e.message)
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