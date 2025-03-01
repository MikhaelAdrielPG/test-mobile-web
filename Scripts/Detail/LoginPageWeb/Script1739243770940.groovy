import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import org.junit.After
import org.openqa.selenium.Keys as Keys
import helpers.GlobalHelper as Helper
import helpers.Tars
import pages.LoginPageWeb
import helpers.Constants as Const

Tars tars = Tars.getInstance()
LoginPageWeb loginPage = new LoginPageWeb()

tars.createReport("LoginWeb", "TC-01")

'Step 1: Open Browser and Navigate to App URL'
Helper.openBrowser(GlobalVariable.AppUrl)

'Step 2: Login using valid credentials'
loginPage.login(GlobalVariable.UsernameWeb, GlobalVariable.PasswordWeb)

'Step 3: Verify successful login'
loginPage.verifyLoginSuccess()

'Step 4: Navigate to Admin Page'
loginPage.navigateToAdminMenu()

'Step 5 : Input Username'
loginPage.inputUsername(Username)

'Step 6 : Select User Role'
loginPage.selectUserRole(UserRole)

'Step 7 : Input Employee Name'
loginPage.inputEmployeeName(EmployeeName)

'Step 8 : Select Status'
loginPage.selectStatus(Status)

'Step 9 : Click Search'
loginPage.clickSearch()

'Step 10 : Close Browser'
Helper.closeBrowser()

tars.saveReport()