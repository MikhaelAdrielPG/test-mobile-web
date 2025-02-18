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
import helpers.Tars
import helpers.TarsStatus
import helpers.Constants as Const
import locators.LoginPageWebLocator as Locator
import helpers.GlobalHelper


import internal.GlobalVariable

public class LoginPageWeb extends BasePage{
	// ini dilakukan di setiap page
	private Tars tars;

	public LoginPageWeb() {
		this.tars = Tars.getInstance()
	}

	@Keyword
	def public void login(String username, String password) {
		WebUI.setText(findByXPath(Locator.usernameInputXPath), username)
		WebUI.setEncryptedText(findByXPath(Locator.passwordInputXPath), password)

		tars.screenshot("Login", "Login Success", TarsStatus.PASSED, 3)

		WebUI.click(findByXPath(Locator.loginButtonXPath))
	}

	@Keyword
	def public void verifyElementPresentByXPath(String xpath, int timeout) {
		boolean isPresent = WebUI.verifyElementPresent(findByXPath(xpath), timeout)

		if (isPresent) {
			KeywordUtil.logInfo("Element found: " + xpath)
		} else {
			KeywordUtil.markFailed("Element not found: " + xpath)
		}
	}

	@Keyword
	def public void verifyLoginSuccess() {
		verifyElementPresentByXPath(Locator.dashboardXPath, 3)
	}

	@Keyword
	def public void navigateToAdminMenu() {
		WebUI.waitForElementPresent(findByXPath(Locator.adminMenu), 3)
		WebUI.click(findByXPath(Locator.adminMenu))
	}

	@Keyword
	def public void inputUsername(String username) {
		WebUI.setText(findByXPath(Locator.usernameField), username)
	}

	@Keyword
	def public void inputEmployeeName(String employeeName) {
		WebUI.setText(findByXPath(Locator.employeeNameField), employeeName)
	}

	@Keyword
	def public void selectUserRole(String userRole) {
		selectDropdownOptionByText(Locator.userRoleDropdown, userRole)
	}

	@Keyword
	def public void selectStatus(String status) {
		selectDropdownOptionByText(Locator.statusDropdown, status)
	}

	@Keyword
	def public void clickSearch() {
		WebUI.click(findByXPath(Locator.searchButton))
		tars.screenshot("Search", "Search Success", TarsStatus.PASSED, 3)
	}

	def private void selectDropdownOptionByText(String dropdownXPath, String value) {
		WebUI.waitForElementPresent(findByXPath(dropdownXPath), 3)
		WebUI.click(findByXPath(dropdownXPath))

		String optionXPath = "//div[@class='oxd-select-option' and span[text()='${value}']]";

		WebUI.waitForElementPresent(findByXPath(optionXPath), 3)
		WebUI.click(findByXPath(optionXPath))
	}
}