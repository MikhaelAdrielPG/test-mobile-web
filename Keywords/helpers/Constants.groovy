package helpers

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

public class Constants {
	public static final String TC001 = "TC_001"
	public static final String TC002 = "TC_002"
	public static final String S001 = "S_001"
	public static final String S002 = "S_002"
	public static final String S003 = "S_003"
	public static final String S004 = "S_004"
	public static final String FINISH_BUTTON = "FINISH"
	public static final String SHEET1 = "Sheet1"
	public static final String SHEET2 = "Sheet2"
	public static final String WEB_TEST_DATA_PATH_XLS = "Data Files/xls/data.xlsx"
	public static final String WEB_TEST_DATA_PATH_CSV = "csv/data"
	public static final String WEB_TEST_DATA_PATH_JSON = "Data Files/json/data.json"
	public static final String MOBILE_TEST_DATA_PATH_XLS = "Data Files/xls/data.xlsx"
	public static final String MOBILE_TEST_DATA_PATH_CSV = "csv/checkout"
	public static final String MOBILE_TEST_DATA_PATH_JSON = "Data Files/json/checkout.json"
}
