package employeesTest;

import org.testng.annotations.Test;

import employeesPages.EmployeeCreation;
import global.Globaldriver;

public class TestEmployees extends Globaldriver {

	
//1. Write a test to confirm an employee can't be added without any part of the form incomplete and confirm the error messages.
@Test
	public void testMandateFieldsEmployee() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testMandateFields();
	}
//2. Write a test to delete an employee and confirm it's been removed.
	@Test
	public void testDeleteEmployee() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testDeleteEmployee();
	}
// 3. Write a test to confirm an advertisement appears after every two employees are added.
	@Test
	public void testAdvertisements() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testAdvertisementAfterAddingTwoEmployees();
	}
// 4. Write a test with a loop that adds 9 users. Then delete users 1, 2, 10 (in this order), and then dismiss all of the ad
	@Test
	public void testCreateDeleteEmployeesAndRemoveAdvertisement() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testCreateDeleteEmployeesAndRemoveAdvertisements();
	}
}
