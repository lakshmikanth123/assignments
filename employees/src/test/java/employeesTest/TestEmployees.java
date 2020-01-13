package employeesTest;

import org.testng.annotations.Test;

import employeesPages.EmployeeCreation;
import global.Globaldriver;

public class TestEmployees extends Globaldriver {

	@Test
	public void testMandateFieldsEmployee() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testMandateFields();
	}

	@Test
	public void testDeleteEmployees() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testDeleteEmployee();
	}

	@Test
	public void testAdvertisements() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testAdvertisementAfterAddingTwoEmployees();
	}

	@Test
	public void testCreateDeleteEmployeesAndRemoveAdvertisement() throws Exception {
		EmployeeCreation mandateFields = new EmployeeCreation(getDriver());
		mandateFields.testCreateDeleteEmployeesAndRemoveAdvertisements();
	}
}
