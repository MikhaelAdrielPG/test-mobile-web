package helpers

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.reader.CSVReader
import com.kms.katalon.core.testdata.reader.CSVSeparator
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.JsonBuilder
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import org.apache.poi.xssf.usermodel.*
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.apache.poi.xwpf.usermodel.XWPFTableRow

import java.io.File
import java.io.FileInputStream
import java.util.regex.Matcher

import internal.GlobalVariable

public class GlobalHelper {
	// Test Object Helper
	@Keyword
	static TestObject findByXPath(String xpath) {
		if (!xpath?.trim()) {
			KeywordUtil.logInfo("Invalid XPath: cannot be null or empty")
			return null
		}

		TestObject dynamicObject = new TestObject()
		dynamicObject.addProperty("xpath", ConditionType.EQUALS, xpath)


		return dynamicObject
	}

	// Mobile Helper
	@Keyword
	static void openApp(String appId) {
		if (appId?.trim()) {
			try {
				Mobile.startExistingApplication(appId)
				KeywordUtil.logInfo("App opened successfully: " + appId)
			} catch (Exception e) {
				KeywordUtil.logInfo("Error opening app: " + e.message)
			}
		} else {
			KeywordUtil.logInfo("Invalid app ID provided.")
		}
	}

	@Keyword
	static void closeApp() {
		try {
			Mobile.closeApplication()
			KeywordUtil.logInfo("App closed successfully")
		} catch (Exception e) {
			KeywordUtil.logInfo("Error closing app: " + e.message)
		}
	}

	// Web Helper
	@Keyword
	static void openBrowser(String url) {
		try {
			WebUI.openBrowser('')
			WebUI.navigateToUrl(url)
			KeywordUtil.logInfo("Browser opened and navigated to: " + url)
		} catch (Exception e) {
			KeywordUtil.logInfo("Error opening browser: " + e.message)
		}
	}

	@Keyword
	static void closeBrowser() {
		try {
			WebUI.closeBrowser()
			KeywordUtil.logInfo("Browser closed successfully")
		} catch (Exception e) {
			KeywordUtil.logInfo("Error closing browser: " + e.message)
		}
	}

	@Keyword
	static void selectDropdownOptionWeb(TestObject dropdown, String optionText) {
		try {
			WebUI.waitForElementVisible(dropdown, 10)
			WebUI.selectOptionByLabel(dropdown, optionText, false)
			KeywordUtil.logInfo("Selected option: " + optionText)
		} catch (Exception e) {
			KeywordUtil.logInfo("Error selecting dropdown option: " + e.message)
		}
	}

	// Read File Helper
	@Keyword
	static Map LoadTestData(String scenarioId, String filePath, int dataStartIdx = 1) {
		def testData = [:]
		testData["ScenarioId"] = scenarioId
		testData["TestDataFilePath"] = filePath
		readTestDataXLS(testData, dataStartIdx)

		if (testData.size() > 0) {
			testData["TestDataXLS"].each { rows ->
				print "Loading Test Data --> "
				rows.each { key, value ->
					print "$key : $value "
				}
				println ""
			}
		} else {
			println "No data loaded (or file/sheet not found) for Scenario: '$scenarioId' in File: '$filePath'"
		}
		return testData
	}

	@Keyword
	static Map readTestDataXLS(Map testData, int dataStartIdx) {
		def columns = []
		def rows = []
		testData["TestDataXLS"] = rows
		FileInputStream fis
		XSSFWorkbook workbook
		XSSFSheet worksheet
		try {
			File xlsFile = new File(testData["TestDataFilePath"])
			if (!xlsFile.exists()) {
				println("Target file is not found in path: " + xlsFile.canonicalPath)
				WebUI.comment("Target file is not found in path: " + xlsFile.canonicalPath)
			}
			fis = new FileInputStream(xlsFile)
			workbook = new XSSFWorkbook(fis)
			def maxLength = Math.min(testData["ScenarioId"].length(), 31)
			def scenarioId = testData["ScenarioId"].toString().substring(0, maxLength)
			worksheet = workbook.getSheet(scenarioId)
			XSSFRow columnRow = worksheet.getRow(0 + dataStartIdx)
			int colIndex = 0
			while (columnRow.getCell(colIndex)) {
				XSSFCell columnCell = columnRow.getCell(colIndex)
				columns[colIndex] = columnCell.toString()
				colIndex++
			}
			int rowIndex = 1 + dataStartIdx
			while (worksheet.getRow(rowIndex)) {
				XSSFRow dataRow = worksheet.getRow(rowIndex)
				def data = [:]
				for (int i = 0; i < colIndex; i++) {
					XSSFCell dataCell = dataRow.getCell(i)
					String cellValue = dataCell.toString()
					if (cellValue.trim().endsWith(".0")) {
						cellValue = cellValue.trim().substring(0, cellValue.length() - 2)
					}
					data[columns[i]] = cellValue
				}
				rows.add(data)
				rowIndex++
			}
		} catch (Exception ex) {
			println("ERROR: " + ex.message)
		} finally {
			if (workbook) workbook.close()
			if (fis) fis.close()
			println("Test Data Load completed!")
		}
	}

	@Keyword
	static Map LoadTestDataCSV(String scenarioId, String csvFileName) {
		String sourceUrl = GlobalVariable.DATA_LOCATION + csvFileName
		CSVReader csv = new CSVReader(sourceUrl, CSVSeparator.COMMA, true)
		List<List<String>> csvData = csv.getData()
		Matcher mapMatcher
		def testData = [:]
		csvData.each { line ->
			if (line[0] == scenarioId) {
				for (int i = 2; i < line.size(); i++) {
					if (line[i]) {
						mapMatcher = line[i] =~ /^\(\w+):\s*(.*)/
						testData[mapMatcher[0][1]] = mapMatcher[0][2]
					}
				}
			}
		}
		println testData
		return testData
	}
}
