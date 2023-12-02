package com.antonsmart.appiumkotlinjunit

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.util.concurrent.TimeUnit

class AppiumSetup {
    private var driver: AppiumDriver<MobileElement>?= null
    private val caps = DesiredCapabilities()

    private val appPackage = "com.antonsmart.listview"
    private val activityName = "com.antonsmart.listview.MainActivity"
    private val automationName = "UiAutomator2"
    private val platformName = "Android"
    private val deviceName = "HUAWEI Y9"
    private val serverUrl = "http://127.0.0.1:4723/wd/hub"

    private val userField = "com.antonsmart.listview:id/user"
    private val nameField = "com.antonsmart.listview:id/name"
    private val numField = "com.antonsmart.listview:id/telephone"
    private val button = "com.antonsmart.listview:id/nextUser"
    private val buttonAdd = "com.antonsmart.listview:id/addUser"

    fun setUp() {
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName)
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName)
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName)
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage)
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, activityName)
        caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true)

        driver = AndroidDriver(URL(serverUrl), caps)
    }

    fun runTest() {
        val wait = WebDriverWait(driver, 10)

        // Haga clic en el botón "Agregar" para abrir la alerta.
        val addButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(buttonAdd)))
        addButton.click()

        val usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(userField)))
        val nameFields = driver?.findElementById(nameField)
        val telephoneField = driver?.findElementById(numField)
        val submitButton = driver?.findElementById(button)

        //Verifica que el campo de texto esté presente
        assert(usernameField != null)
        assert(nameFields != null)
        assert(telephoneField != null)

        //Ingresar texto en el campo de texto
        usernameField?.sendKeys("UsuarioEjemplo")
        nameFields?.sendKeys("Menganito")
        telephoneField?.sendKeys("123456789")

        //Verificar que el campo de texto se haya ingresado correctamente
        assert(usernameField?.text == "UsuarioEjemplo")
        assert(nameFields?.text == "Menganito")
        assert(telephoneField?.text == "123456789")

        //Hacer clic en el botón de enviar
        submitButton?.click()

        // Esperar a que la aplicación realice las operaciones
        TimeUnit.SECONDS.sleep(5)

    }


    fun tearDown() {
        driver?.quit()
    }

    @Test
    fun main() {
        val test = AppiumSetup()
        test.setUp()
        test.runTest()
        test.tearDown()
    }
}