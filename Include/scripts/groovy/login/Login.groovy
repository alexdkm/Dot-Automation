package login
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
import com.kms.katalon.core.webui.constants.WebUICommonScriptConstants
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import java.lang.invoke.SwitchPoint
import java.time.LocalDateTime
import groovy.transform.ConditionalInterrupt
import com.kms.katalon.core.configuration.RunConfiguration
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap.KeySetView
import java.util.concurrent.locks.Condition

import com.kms.katalon.core.testobject.ConditionType
import internal.GlobalVariable
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import javax.swing.JOptionPane
import com.kms.katalon.core.util.KeywordUtil



class Login {
	public void functionLogin(String pathFile,row) {
		String email = findTestData(pathFile).getValue('Email', row)
		String password = findTestData(pathFile).getValue('Password', row)

		boolean verifyInputEmail = WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/Input_Email'), 5, FailureHandling.OPTIONAL)
		boolean verifyInputPassword = WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/Input_Password'), 5, FailureHandling.OPTIONAL)

		if (verifyInputEmail && verifyInputPassword) {
			if (!(email == "")) {
				if (email.contains("@") && email.contains(".")) {
					WebUI.setText(findTestObject('Object Repository/Homepage/Input_Email'), email)
				} else {
					JOptionPane.showMessageDialog(null, "Input Email Tidak Valid", "", JOptionPane.INFORMATION_MESSAGE)
					WebUI.takeScreenshot("Screenshots/InputEmailTidakValid.png")
					throw new Exception("Error: Input Email Tidak Valid")
				}
			} else {
				JOptionPane.showMessageDialog(null, "Data Email Kosong", "", JOptionPane.INFORMATION_MESSAGE)
				WebUI.takeScreenshot("Screenshots/DataEmailKosong.png")
				throw new Exception("Error: Email Kosong")
			}

			if (!(password == "")) {
				if (password.length() >= 6 && password.length() <= 20) {
					WebUI.setText(findTestObject('Object Repository/Homepage/Input_Password'), password)
				} else {
					JOptionPane.showMessageDialog(null, "Input Password Tidak Valid", "", JOptionPane.INFORMATION_MESSAGE)
					WebUI.takeScreenshot("Screenshots/InputPasswordTidakValid.png")
					throw new Exception("Error: Input Password Tidak Valid")
				}
			} else {
				JOptionPane.showMessageDialog(null, "Data Password Kosong", "", JOptionPane.INFORMATION_MESSAGE)
				WebUI.takeScreenshot("Screenshots/DataPasswordKosong.png")
				throw new Exception("Error: Password Kosong")
			}

			boolean verifyButtonLogin = WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/button_Login'), 5, FailureHandling.OPTIONAL)
			if (verifyButtonLogin) {
				WebUI.click(findTestObject('Object Repository/Homepage/button_Login'))

				TestObject textWelcome = new TestObject()
				textWelcome.addProperty("xpath", ConditionType.EQUALS, "(//span[contains(text(),'Selamat datang di Facebook')])[2]")

				boolean verifyTextWelcome = WebUI.waitForElementPresent(textWelcome, 5, FailureHandling.OPTIONAL)
				if (verifyTextWelcome) {
					JOptionPane.showMessageDialog(null, "Berhasil Login", "", JOptionPane.INFORMATION_MESSAGE)
					WebUI.takeScreenshot("Screenshots/BerhasilLogin.png")
				} else {
					JOptionPane.showMessageDialog(null, "Gagal Login: Teks Selamat datang tidak ditemukan", "", JOptionPane.INFORMATION_MESSAGE)
					WebUI.takeScreenshot("Screenshots/GagalLogin.png")
				}
			} else {
				JOptionPane.showMessageDialog(null, "Button Login Tidak Ditemukan", "", JOptionPane.INFORMATION_MESSAGE)
				WebUI.takeScreenshot("Screenshots/ButtonLoginTidakDitemukan.png")
			}
		} else {
			JOptionPane.showMessageDialog(null, "Input Field Email / Password Tidak Ditemukan", "", JOptionPane.INFORMATION_MESSAGE)
			WebUI.takeScreenshot("Screenshots/InputFieldTidakDitemukan.png")
		}
	}
}
