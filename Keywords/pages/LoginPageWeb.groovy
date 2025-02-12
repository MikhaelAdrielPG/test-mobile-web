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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import helpers.GlobalHelper as Helper


import internal.GlobalVariable

public class LoginPageWeb {

	private final String usernameInputXPath = "//input[@name='username']"
	private final String passwordInputXPath = "//input[@name='password']"
	private final String loginButtonXPath = "//button[contains(@class,'oxd-button')]"
	private final String dashboardXPath = "//*[@id='app']/div[1]/div[1]/header/div[1]/div[1]/span/h6"

	@Keyword
	public void login(String username, String password) {
		WebUI.setText(Helper.findByXPath(usernameInputXPath), username)
		WebUI.setEncryptedText(Helper.findByXPath(passwordInputXPath), password)
		WebUI.click(Helper.findByXPath(loginButtonXPath))
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
		verifyElementPresentByXPath(dashboardXPath, 10)
	}
}