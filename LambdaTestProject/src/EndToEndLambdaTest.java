
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * 
 */

/**
 * @author Shivam
 *
 */

public class EndToEndLambdaTest {

	/**
	 * @param args
	 * @throws InterruptedException
	 */

	String username = "seleniumcloudtestuser";
	String accessKey = "BT0GDd0rkAtGNKaA7nFgOVtGQl40WZk1aJf8qxe2nVBbdtYf67";
	int runNumber = 0;

	@Test
	public void main() throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		/*
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\Shivam\\Documents\\eclipse-workspace\\LambdaTestProject\\Dependencies\\chromedriver.exe"
		 * );
		 */

		// FirefoxOptions options = new FirefoxOptions();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-popup-blocking:", "--disable-notifications");

		// DesiredCapabilities options = new DesiredCapabilities();
		/*
		 * //options.setCapability(FirefoxOptions.FIREFOX_OPTIONS,options);
		 * 
		 * options.setCapability("platform", "Windows 10");
		 * options.setCapability("browserName", "Firefox");
		 * options.setCapability("version", "86.0"); // If this cap isn't specified, it
		 * 
		 * options.setCapability("build", "LambdaTest");
		 * 
		 * options.setCapability("name", "LambdaTestRuns");
		 * options.setCapability("network", true); // To enable network logs
		 * options.setCapability("visual", true); // To enable step by step screenshot
		 * options.setCapability("video", true); // To enable video recording
		 * options.setCapability("console", true); // To capture console logs
		 * 
		 * WebDriver driver = new RemoteWebDriver(new URL("https://" + username + ":" +
		 * accessKey + "@hub.lambdatest.com/wd/hub"), options);
		 */

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://www.lambdatest.com/automation-demos/");

		driver.findElement(By.id("username")).sendKeys("lambda");
		driver.findElement(By.id("password")).sendKeys("lambda123");
		driver.findElement(By.className("applynow")).click();

		var loginSuccessText = driver.findElement(By.cssSelector("div.toast.jam")).getText();

		Assert.assertTrue(loginSuccessText.contains("Successully Login"));
		driver.findElement(By.id("developer-name")).sendKeys("ShivamLambdaTest@gmail.com");
		driver.findElement(By.id("populate")).click();
		var alert = driver.switchTo().alert();
		var alertText = alert.getText();
		Assert.assertTrue(alertText.contains("ShivamLambdaTest"));
		alert.accept();
		driver.findElement(By.id("month")).click();

		var IsSelected = driver.findElement(By.id("month")).isSelected();
		Assert.assertTrue(IsSelected);
		driver.findElement(By.id("customer-service")).click();
		Select paymentMode = new Select(driver.findElement(By.id("preferred-payment")));
		paymentMode.selectByIndex(0);
		driver.findElement(By.id("tried-ecom")).click();
		var slider = driver.findElement(By.id("slider"));
		var IsSliderEnable = slider.isEnabled();

		Assert.assertTrue(IsSliderEnable);

		var js = ((JavascriptExecutor) driver);

		var sliderElement = driver.findElement(By.cssSelector(".ui-slider-handle.ui-corner-all.ui-state-default"));

		js.executeScript("window.scrollBy(0,300)");
		Actions action = new Actions(driver);
		action.dragAndDropBy(sliderElement, sliderElement.getSize().getWidth() * 23, 0).build().perform();
		var actualSliderPerct = driver.findElement(By.cssSelector(".ui-slider-handle.ui-corner-all.ui-state-default"))
				.getAttribute("style");

		Assert.assertTrue(actualSliderPerct.contains("88.8889"));

		js.executeScript("window.open();");

		var windows = driver.getWindowHandles().toArray();
		driver.switchTo().window((String) windows[1]);
		driver.get("https://www.lambdatest.com/selenium-automation");

		var element = driver.findElement(By.xpath("//div[@class='citoolbox']"));
		js.executeScript("arguments[0].scrollIntoView();", element);

		var jenkinsImage = driver.findElement(
				By.xpath("//img[@src='https://cdn.lambdatest.com/assets_black_theme/images/selenium/jenkins.svg']"));

		DownloadImage(jenkinsImage.getAttribute("src"), "src/jenkins.svg");
		driver.switchTo().window((String) windows[0]);
		driver.findElement(By.id("comments")).sendKeys("Test Comments");
		var uploadDiv = driver.findElement(By.className("upload-img"));
		js.executeScript("arguments[0].scrollIntoView();", uploadDiv);
		uploadDiv.findElement(By.id("img")).click();
		File file = new File("src/FileUpload.exe");
		String excPath = file.getAbsolutePath();

		File file2 = new File("src/jenkins.svg");
		String imagePath = file2.getAbsolutePath();
		String Command = excPath + " " + imagePath;
		Runtime.getRuntime().exec(Command);
		Thread.sleep(5000);
		var uploadAlert = driver.switchTo().alert();
		uploadAlert.accept();
		driver.findElement(By.id("submit-button")).click();
		String successfullSubmissionMesg = driver.findElement(By.xpath("//div[@id='message']//p")).getText();
		String expectedMsg = "You have successfully submitted the form.";

		Assert.assertTrue(successfullSubmissionMesg.equalsIgnoreCase(expectedMsg));

		driver.quit();

	}

	private void DownloadImage(String imageSrc, String filePath) {

		try {

	
			URL imageURL = new URL(imageSrc);

			

			InputStream is = imageURL.openStream();
			FileOutputStream os = new FileOutputStream(filePath);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
