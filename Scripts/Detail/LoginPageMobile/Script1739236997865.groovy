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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import helpers.GlobalHelper
import pages.LoginPageMobile
import helpers.Tars
import helpers.Constants as Const

Tars tars = Tars.getInstance()
LoginPageMobile loginPage = new LoginPageMobile()

'Step 1: Open App'
GlobalHelper.openApp(GlobalVariable.AppPath) 

'Step 2: Login'
loginPage.login(GlobalVariable.UsernameMobile, GlobalVariable.PasswordMobile)

'Step 3: Verify Login'
loginPage.verifyLoginSuccess()

'Step 4 : Tap Add To Chart'
loginPage.tapAddToChart()

'Step 5 : Tap Chart'
loginPage.tapChart()

'Step 6 : Tap Checkout'
loginPage.tapCheckout()

'Step 7 : Input First Name'
loginPage.inputFirstName(FirstName)

'Step 8 : Input Last Name'
loginPage.inputLastName(LastName)

'Step 9 : Input Postal Code'
loginPage.inputPostalCode(PostalCode)

'Step 10 : Tap Continue'
loginPage.tapContinue()

'Step 11 : Tap Finish'
loginPage.tapFinish()

'Step 12 : Close App'
GlobalHelper.closeApp()

tars.saveReport()