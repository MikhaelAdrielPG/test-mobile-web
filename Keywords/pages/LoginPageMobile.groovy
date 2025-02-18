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
import helpers.Tars
import helpers.TarsStatus
import helpers.GlobalHelper as Helper

import internal.GlobalVariable

public class LoginPageMobile extends BasePage {
	private Tars tars;
	
	public LoginPageWeb() {
		this.tars = Tars.getInstance()
	}
	
	
	@Keyword
	def public void login(String username, String password) {
		Mobile.setText(findByXPath(Locator.usernameXPath), username, 3)
		Mobile.setText(findByXPath(Locator.passwordXPath), password, 3)
		
		tars.screenshot("Login", "Login Success", TarsStatus.PASSED, 3)
		
		Mobile.tap(findByXPath(Locator.loginButtonXPath), 3)
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
		verifyElementPresentByXPath(Locator.dashboardXPath, 3)
	}

	@Keyword
	def public inputFirstName(String firstName) {
		Mobile.setText(findByXPath(Locator.txtFirstName), firstName, 3)
	}

	@Keyword
	def public inputLastName(String lastName) {
		Mobile.setText(findByXPath(Locator.txtLastName), lastName, 3)
	}

	@Keyword
	def public inputPostalCode(String postalCode) {
		Mobile.setText(findByXPath(Locator.txtZip), postalCode, 3)
		Mobile.hideKeyboard()
	}

	@Keyword
	def public tapAddToChart() {
		Mobile.tap(findByXPath(Locator.addToChart), 3)
	}

	@Keyword
	def public tapChart() {
		Mobile.tap(findByXPath(Locator.btnChart), 3)
	}

	@Keyword
	def public tapCheckout() {
		Mobile.tap(findByXPath(Locator.btnCheckout), 3)
		tars.screenshot("Checkout", "Checkout Success", TarsStatus.PASSED, 3)
	}

	@Keyword
	def public tapContinue() {
		Mobile.tap(findByXPath(Locator.btnContinue), 3)
		Mobile.scrollToText(Const.FINISH_BUTTON)
	}

	@Keyword
	def public tapFinish() {
		Mobile.tap(findByXPath(Locator.btnFinish), 3)
	}
}