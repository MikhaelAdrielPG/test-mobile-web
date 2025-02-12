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

public class LoginPageMobile {

	private String usernameXPath = "//*[@class = 'android.widget.EditText' and (@text = 'Username' or . = 'Username')]"
	private String passwordXPath = "//*[@class = 'android.widget.EditText' and (@text = 'Password' or . = 'Password')]"
	private String loginButtonXPath = "//*[@class = 'android.widget.TextView' and (@text = 'LOGIN' or . = 'LOGIN')]"
	private String dashboardXPath = "//*[@class = 'android.widget.TextView' and (@text = 'PRODUCTS' or . = 'PRODUCTS')]"
	private String product = "//*[@class = 'android.widget.TextView' and (@text = 'Sauce Labs Onesie' or . = 'Sauce Labs Onesie')]"
	private String addToChart = "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.TextView[1]"
	private String btnChart = "//*[@class = 'android.widget.TextView' and (@text = '1' or . = '1')]"
	private String btnCheckout = "//*[@class = 'android.widget.TextView' and (@text = 'CHECKOUT' or . = 'CHECKOUT')]"
	private String txtFirstName = "//*[@class = 'android.widget.EditText' and (@text = 'First Name' or . = 'First Name')]"
	private String txtLastName = "//*[@class = 'android.widget.EditText' and (@text = 'Last Name' or . = 'Last Name')]"
	private String txtZip = "//*[@class = 'android.widget.EditText' and (@text = 'Zip/Postal Code' or . = 'Zip/Postal Code')]"
	private String btnContinue = "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[7]/android.widget.TextView[1]"
	private String btnCancel = "//*[@class = 'android.widget.TextView' and (@text = 'CANCEL' or . = 'CANCEL')]"
	private String btnFinish = "//*[@class = 'android.widget.TextView' and (@text = 'FINISH' or . = 'FINISH')]"
	private String checkoutInfo = "//*[@class = 'android.widget.TextView' and (@text = 'CHECKOUT: INFORMATION' or . = 'CHECKOUT: INFORMATION')]"
	private String reserved = "//*[@class = 'android.widget.TextView' and (@text = '© 2025 Sauce Labs. All Rights Reserved.' or . = '© 2025 Sauce Labs. All Rights Reserved.')]"
	
	@Keyword
	public void login(String username, String password) {
		Mobile.setText(Helper.findByXPath(usernameXPath), username, 10)
		Mobile.setText(Helper.findByXPath(passwordXPath), password, 10)
		Mobile.tap(Helper.findByXPath(loginButtonXPath), 10)
	}

	@Keyword
	public void verifyElementPresentByXPath(String xpath, int timeout) {
		boolean isPresent = Mobile.verifyElementExist(Helper.findByXPath(xpath), timeout)

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

	@Keyword
	public void checkout(String firstName, String lastName, String postalCode) {
		Mobile.tap(Helper.findByXPath(addToChart), 3)
		Mobile.tap(Helper.findByXPath(btnChart), 3)
		Mobile.tap(Helper.findByXPath(btnCheckout), 3)
		Mobile.setText(Helper.findByXPath(txtFirstName), firstName, 1)
		Mobile.setText(Helper.findByXPath(txtLastName), lastName, 1)
		Mobile.setText(Helper.findByXPath(txtZip), postalCode, 1)
		Mobile.hideKeyboard()
		Mobile.tap(Helper.findByXPath(btnContinue), 3)
		Mobile.scrollToText("FINISH")
		Mobile.tap(Helper.findByXPath(btnFinish), 3)
	}
}
