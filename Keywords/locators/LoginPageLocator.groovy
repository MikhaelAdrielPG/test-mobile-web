package locators

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

import internal.GlobalVariable

public class LoginPageLocator {

	// Web Login XPaths
	public static final String USERNAME_INPUT_WEB = "//input[@name='username']"
	public static final String PASSWORD_INPUT_WEB = "//input[@name='password']"
	public static final String LOGIN_BUTTON_WEB = "//button[contains(@class,'oxd-button')]"
	public static final String DASHBOARD_WEB = "//*[@id='app']/div[1]/div[1]/header/div[1]/div[1]/span/h6"

	// Mobile Login XPaths
	public static final String USERNAME_INPUT_MOBILE = "//*[@class='android.widget.EditText' and (@text='Username' or .='Username')]"
	public static final String PASSWORD_INPUT_MOBILE = "//*[@class='android.widget.EditText' and (@text='Password' or .='Password')]"
	public static final String LOGIN_BUTTON_MOBILE = "//*[@class='android.widget.TextView' and (@text='LOGIN' or .='LOGIN')]"
	public static final String DASHBOARD_MOBILE = "//*[@class='android.widget.TextView' and (@text='PRODUCTS' or .='PRODUCTS')]"
}
