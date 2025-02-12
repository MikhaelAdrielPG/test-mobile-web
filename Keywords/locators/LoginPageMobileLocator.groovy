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

public class LoginPageMobileLocator {
	public static final String usernameXPath = "//*[@class = 'android.widget.EditText' and (@text = 'Username' or . = 'Username')]"
	public static final String passwordXPath = "//*[@class = 'android.widget.EditText' and (@text = 'Password' or . = 'Password')]"
	public static final String loginButtonXPath = "//*[@class = 'android.widget.TextView' and (@text = 'LOGIN' or . = 'LOGIN')]"
	public static final String dashboardXPath = "//*[@class = 'android.widget.TextView' and (@text = 'PRODUCTS' or . = 'PRODUCTS')]"
	public static final String addToChart = "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.widget.TextView[1]"
	public static final String btnChart = "//*[@class = 'android.widget.TextView' and (@text = '1' or . = '1')]"
	public static final String btnCheckout = "//*[@class = 'android.widget.TextView' and (@text = 'CHECKOUT' or . = 'CHECKOUT')]"
	public static final String txtFirstName = "//*[@class = 'android.widget.EditText' and (@text = 'First Name' or . = 'First Name')]"
	public static final String txtLastName = "//*[@class = 'android.widget.EditText' and (@text = 'Last Name' or . = 'Last Name')]"
	public static final String txtZip = "//*[@class = 'android.widget.EditText' and (@text = 'Zip/Postal Code' or . = 'Zip/Postal Code')]"
	public static final String btnContinue = "//hierarchy/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.widget.ScrollView[1]/android.view.ViewGroup[1]/android.view.ViewGroup[7]/android.widget.TextView[1]"
	public static final String btnCancel = "//*[@class = 'android.widget.TextView' and (@text = 'CANCEL' or . = 'CANCEL')]"
	public static final String btnFinish = "//*[@class = 'android.widget.TextView' and (@text = 'FINISH' or . = 'FINISH')]"
}
