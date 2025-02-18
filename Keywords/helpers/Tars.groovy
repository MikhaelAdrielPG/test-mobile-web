package helpers;

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.UUID

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.HttpBodyContent
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.impl.HttpFileBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper
import internal.GlobalVariable

public class Tars {
	private static Tars tarsInstance = null
	private baseUrl
	private projectName
	private toolName
	private activityName
	private authorName
	private token

	Tars() {
		this.baseUrl = GlobalVariable.TarsUrlLocal
		this.projectName = GlobalVariable.ProjectName
		this.activityName = GlobalVariable.ActivityName
		this.toolName = GlobalVariable.ToolName
		this.authorName = GlobalVariable.AuthorName
	}

	public static synchronized Tars getInstance() {
		if (tarsInstance == null) {
			tarsInstance = new Tars()
		}
		return tarsInstance
	}

	@Keyword
	public createReport(String scenario, String testCase) {
		try {
			def String reportData = "{\"project\" : \"${this.projectName}\", \"scenario\": \"${scenario}\", \"test_case\": \"${testCase}\", \"tool\": \"${this.toolName}\", \"activity\": \"${this.activityName}\", \"author\": \"${this.authorName}\"}"

			List<TestObjectProperty> headers = []
			headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))

			def RequestObject reqObject = new RestRequestObjectBuilder()
					.withRestUrl("${this.baseUrl}/api/create-report")
					.withRestRequestMethod("POST")
					.withHttpHeaders(headers)
					.withTextBodyContent(reportData)
					.build()

			def ResponseObject resObject = WS.sendRequest(reqObject)
			def String responseBody = this.validateResponse(resObject)

			this.token = new JsonSlurper().parseText(responseBody).data.token as String
		} catch (Exception e) {
			println(e.getMessage())
			throw e
		}
	}

	private int addTestImage(String imagePath) {
		try {
			List<TestObjectProperty> headers = []
			headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/octet-stream"))
			headers.add(new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer ${this.token}"))

			def RequestObject reqObject = new RequestObject()
			reqObject.setRestUrl("${this.baseUrl}/api/add-test-image")
			reqObject.setRestRequestMethod("POST")
			reqObject.setHttpHeaderProperties(headers)
			reqObject.setBodyContent(new HttpFileBodyContent(imagePath))

			def ResponseObject resObject = WS.sendRequest(reqObject)
			def String responseBody = this.validateResponse(resObject)

			Files.deleteIfExists(Paths.get(imagePath))

			return new JsonSlurper().parseText(responseBody).data.detail_id as int
		} catch (Exception e) {
			println(e.getMessage())
			throw e
		}
	}

	private addTestStep(int detailId, String title, String desc, TarsStatus tarsStatus ) {

		try {
			def String jsonData = "{\"detail_id\": ${detailId},  \"title\" : \"${title}\", \"description\" : \"${desc}\", \"status\" :  ${tarsStatus.value}}"

			List<TestObjectProperty> headers = []
			headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))
			headers.add(new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer ${this.token}"))

			def RequestObject reqObject = new RestRequestObjectBuilder()
					.withRestUrl("${this.baseUrl}/api/add-test-step")
					.withRestRequestMethod("POST")
					.withHttpHeaders(headers)
					.withTextBodyContent(jsonData)
					.build()

			def ResponseObject resObject = WS.sendRequest(reqObject)
			this.validateResponse(resObject)
		} catch (Exception e) {
			println(e.getMessage())
			throw e
		}
	}

	@Keyword
	public saveReport() {
		try {
			List<TestObjectProperty> headers = []
			headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))
			headers.add(new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer ${this.token}"))

			def RequestObject reqObject = new RestRequestObjectBuilder()
					.withRestUrl("${this.baseUrl}/api/save-report")
					.withRestRequestMethod("POST")
					.withHttpHeaders(headers)
					.build()

			def ResponseObject resObject = WS.sendRequest(reqObject)
			this.validateResponse(resObject)
		} catch (Exception e) {
			println(e.getMessage())
			throw e
		}
	}

	@Keyword
	public saveReportFailed() {
		try {
			List<TestObjectProperty> headers = []
			headers.add(new TestObjectProperty("Content-Type", ConditionType.EQUALS, "application/json"))
			headers.add(new TestObjectProperty("Authorization", ConditionType.EQUALS, "Bearer ${this.token}"))

			def RequestObject reqObject = new RestRequestObjectBuilder()
					.withRestUrl("${this.baseUrl}/api/save-report-failed")
					.withRestRequestMethod("POST")
					.withHttpHeaders(headers)
					.build()

			def ResponseObject resObject = WS.sendRequest(reqObject)
			this.validateResponse(resObject)
		} catch (Exception e) {
			println(e.getMessage())
			throw e
		}
	}


	private String validateResponse(ResponseObject resObject) {
		def int statusCode = resObject.getStatusCode()
		def String responseBody = resObject.getResponseText()

		if (statusCode != 200 && statusCode != 201) {
			println(responseBody)
			throw new Exception("Tars Error ${responseBody}")
		}
		return responseBody
	}

	@Keyword
	public screenshot(String title, String desc, TarsStatus tarsStatus, int timeout = 0) {
		WebUI.delay(timeout)
		def String fileName = RunConfiguration.getProjectDir() + "\\ScreenshotTemp\\" + UUID.randomUUID().toString() + ".png"
		WebUI.takeScreenshot(fileName)
		def detailId = this.addTestImage(fileName)
		this.addTestStep(detailId, title, desc, tarsStatus)
	}
}
