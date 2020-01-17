package employeesPages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import global.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class EmployeeCreation extends BasePage {
	private AppiumDriver<MobileElement> driver;

	public EmployeeCreation(AppiumDriver<MobileElement> driver) {
		super(driver);
		this.driver = driver;
	}
//The below method checks and confirm the error messages, when the user tries to create withot entering mandate fields
	public void testMandateFields() throws Exception {
		clickOnElement(By.id("fab"));
		clickOnElement(By.id("createButton"));
		List<MobileElement> errorMessages = driver
				.findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'validation')]"));
		// Comparing the with the no.of error messages displayed, as we tried to create an employee without providing any detail, error message count should be 4
		assertTrue(errorMessages.size() == 4);
	}// EOM

	//This method selects job and project from the dropdown, jus we need pass the input strings which need to be selected
	public void selectJobAndProject(String jobTitle, String currentProject) throws Exception {
		clickOnElement(By.id("titleSpinner"));
		clickOnElement(By.xpath("//android.widget.TextView[@text='" + jobTitle + "']"));
		driver.hideKeyboard();
		clickOnElement(By.id("projectSpinner"));
		clickOnElement(By.xpath("//android.widget.TextView[@text='" + currentProject + "']"));
		driver.hideKeyboard();
	}
//This will delete one employee and confrims whether it got deleted or not.
	public void testDeleteEmployee() throws Exception {
		List<MobileElement> availableEmployees = driver.findElements(By.id("fullNameTextView"));
		List<String> employeeNames = new ArrayList<String>();
		for (WebElement e : availableEmployees) {
			employeeNames.add(e.getText());
		}
		availableEmployees.get(0).click();
		clickOnElement(By.id("deleteEmployeeButton"));
		List<MobileElement> availableEmployeesAfterDeleting = driver.findElements(By.id("fullNameTextView"));
		List<String> employeeNamesAfterDeleting = new ArrayList<String>();
		Thread.sleep(5000);
		availableEmployeesAfterDeleting = driver.findElements(By.id("fullNameTextView"));
		for (WebElement e : availableEmployeesAfterDeleting) {
			employeeNamesAfterDeleting.add(e.getText());
		}
		// After deleting the first available employee, comparing the new available employee names, they should not be available
		assertNotEquals(employeeNames.get(0), employeeNamesAfterDeleting.get(0));
	}

//This Checks whether advertisement is getting added or not after creating two employees
	public void testAdvertisementAfterAddingTwoEmployees() throws Exception {
		// Initially at position 3, Advertisement will be displayed. So we have declared position as 3.
		int adviewPosition = 3;
		clickOnElement(By.id("fab"));
		enterText(By.id("firstNameEditText"), "NewUser1");
		driver.hideKeyboard();
		enterText(By.id("lastNameEditText"), "NewUser1 lastName");
		driver.hideKeyboard();
		selectJobAndProject("Qa Tester", "Professional");
		clickOnElement(By.id("createButton"));
		clickOnElement(By.id("fab"));
		enterText(By.id("firstNameEditText"), "NewUser2");
		driver.hideKeyboard();
		enterText(By.id("lastNameEditText"), "NewUser2 lastName");
		driver.hideKeyboard();
		selectJobAndProject("Qa Tester", "Professional");
		clickOnElement(By.id("createButton"));
		List<MobileElement> availableEmployees = driver.findElements(By.xpath(
				"//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,'employeesRecyclerView')]/android.widget.LinearLayout"));
		do {
			// creating xpath for advertisement button view of advertisement at position 3 using the linear layout class.
			WebElement advertiser = driver.findElement(By
					.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,'employeesRecyclerView')]/android.widget.LinearLayout["
							+ adviewPosition + "]//android.widget.ImageView"));
			// Validating Advertisement button.
			assertTrue(advertiser.isEnabled());
			// Increasing the position value by 3, as we should (expected) get Advertisement button after adding 2 employees.
			adviewPosition = adviewPosition + 3;
		} while (adviewPosition <= availableEmployees.size());

	}

	//The below method will create employees and delete the selected employees from the list and after that it will delete all the advertisements
	public void testCreateDeleteEmployeesAndRemoveAdvertisements() throws Exception {
		List<String> employees = createEmployee(9, "Test", "Professional");
		// As per the requirement, we need to delete user1: considered user1
		// from the
		// created users (excluded existing users).
		deleteEmployee(employees.get(0));
		// As per the requirement, we need to delete user2: considered user2
		// from the
		// created users (excluded existing users)
		deleteEmployee(employees.get(1));
		// As per the requirement, we need to delete user10: considered user10
		// from the
		// created users (excluded existing users)
		deleteEmployee(employees.get(9));
		scrollDown();
		scrollDown();
		deleteAdvertisement();

	}
//This method will create the employees, just we need to pass the no.of employees and firstName and last name references
	public List<String> createEmployee(int employees, String firstName, String LastName) throws Exception {
		List<String> employeeNames = new ArrayList<String>();
		for (int i = 0; i <= employees; i++) {
			clickOnElement(By.id("fab"));
			enterText(By.id("firstNameEditText"), firstName);
			driver.hideKeyboard();
			enterText(By.id("lastNameEditText"), LastName + i);
			driver.hideKeyboard();
			selectJobAndProject("Qa Tester", "Professional");
			clickOnElement(By.id("createButton"));
			employeeNames.add(firstName + " " + LastName + i);
		}
		return employeeNames;

	}

	//This method will do a scroll up on the device, created using the height and width of the device
	public void scrollUp() throws Exception {
		try {
			int pressX = driver.manage().window().getSize().width / 2;
			int bottomY = driver.manage().window().getSize().height * 2 / 5;
			int topY = driver.manage().window().getSize().height / 8;
			TouchAction touchAction = new TouchAction(driver);
			touchAction.press(PointOption.point(pressX, bottomY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
					.moveTo(PointOption.point(pressX, topY)).release().perform();
		} catch (Exception e) {
			System.out.println("exit scroll");
		}
	}
	//This method will do a scroll down on the device, created using the height and width of the device
	public void scrollDown() throws Exception {
		try {
			int pressX = driver.manage().window().getSize().width / 2;
			int bottomY = driver.manage().window().getSize().height * 2 / 5;
			int topY = driver.manage().window().getSize().height / 8;
			TouchAction touchAction = new TouchAction(driver);
			touchAction.press(PointOption.point(pressX, topY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1300)))
					.moveTo(PointOption.point(pressX, bottomY)).release().perform();
		} catch (Exception e) {
			System.out.println("exit scroll");
		}
	}

	//This will delete employee from the available list. Input we need to pass is the name of the employee
	public void deleteEmployee(String employeeName) throws Exception {
		boolean flag = true;
		scrollDown();
		scrollDown();
		// Creating xpath dynamically using employee name
		do {
			try {
				WebElement employeeNames = driver
						.findElement(By.xpath("//android.widget.TextView[@text='" + employeeName + "']/.."));
				if (employeeNames.isEnabled()) {
					employeeNames.click();
					flag = false;
				} else {
					scrollUp();
				}
			} catch (Exception e) {
				scrollUp();

			}
		} while (flag);
		clickOnElement(By.id("deleteEmployeeButton"));
	}
//This will delete all the advertisements available 
	public void deleteAdvertisement() throws Exception {
		boolean flag = false;
		List<MobileElement> advertisement = driver.findElements(By.id("adView"));
		do {
			try {
				advertisement = driver.findElements(By.id("adView"));
				if (advertisement.size() > 0) {
					advertisement.get(0).click();
					flag = true;
					clickOnElement(By.id("adView"));
				} else if (advertisement.size() == 0)
					scrollUp();
				advertisement = driver.findElements(By.id("adView"));

				{
					if (advertisement.size() == 0) {
						Thread.sleep(111);
						flag = false;
					}

				}
			} catch (Exception e) {
				scrollUp();
				e.printStackTrace();
			}

		} while (flag);

	}
}
