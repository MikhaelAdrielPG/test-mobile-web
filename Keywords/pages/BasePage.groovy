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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import helpers.Tars
import helpers.TarsStatus
import internal.GlobalVariable

public class BasePage {

	private Tars tars

	BasePage() {
		this.tars = Tars.getInstance()
	}

	protected errorHandling(Exception error) {
		this.tars.screenshot("Failed", error.getMessage(), TarsStatus.FAILED)
		this.tars.saveReportFailed()
		WebUI.closeBrowser()
		throw error
	}

	protected expectTrue(Boolean value) {
		if (!value) {
			throw new Exception("Assertion failed! Expected True, Actual False")
		}
	}

	protected expectEqual(Object expected, Object actual) {
		if (expected != actual) {
			throw new AssertionError("Assertion failed! Expected: ${expected}, but got: ${actual}")
		}
	}

	// Test Object Helper
	@Keyword
	def protected TestObject findByXPath(String xpath) {
		if (!xpath?.trim()) {
			KeywordUtil.markError("Invalid XPath: cannot be null or empty")
			return null
		}

		TestObject dynamicObject = new TestObject()
		dynamicObject.addProperty("xpath", ConditionType.EQUALS, xpath)


		return dynamicObject
	}
}
