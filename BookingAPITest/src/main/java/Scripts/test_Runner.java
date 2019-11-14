package Scripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Components.Base;
import Booking.Booking;

public class test_Runner extends Base {

	public static ArrayList<HashMap<String, Object>> Results = new ArrayList<HashMap<String, Object>>();

	HashMap<String, Object> Result;

	public static String token = null;

	public static Integer id = null;

	HashMap<String, Object> postData;

	@BeforeClass

	public void initialise() {

		initialize(); // Declares string builder, writer and log files, prerequisites for generating
						// logs

		readConfigData(); // Storing data from configuration properties
	}

	@BeforeMethod

	public void SetUp(Method method) {

		ANNOTATE("INFO : Test Case Execution : Test Execution STARTED for Test Case - [ " + method.getName() + " ]");
		ANNOTATE(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	@DataProvider(name = "content")

	public Object[][] content() throws IOException {

		return new Object[][] { { "admin", "password123" } };
	}

	@Test(priority = 1,

			dataProvider = "content")

	public void AccessToken(String userName, String passWord) {

		Booking.getAccess_Method(userName, passWord);

		Booking.validateStatusCode_Method(200);

		token = Booking.getAccessToken_Method(userName);
	}

	@DataProvider(name = "postContent")

	public Object[][] postContent() throws IOException {

		return new Object[][] { { "John", "Doe", 111, true, "2018-01-01", "2018-01-01", "Breakfast" } };
	}

	@Test(priority = 2,

			dependsOnMethods = { "AccessToken" },

			dataProvider = "postContent")

	public void API_Post(String firstName, String lastName, Integer price, boolean payment, String checkIn,
			String checkOut, String additional) {

		postData = Booking.addNewBooking_Method(firstName, lastName, price, payment, checkIn, checkOut, additional,
				token);

		Booking.validateStatusCode_Method(200);

		Booking.validateRequestAndResponseData_Post_Method(postData);

		id = Booking.getBookingID_Method(postData);
	}

	@DataProvider(name = "puttContent")

	public Object[][] puttContent() throws IOException {

		return new Object[][] { { "John", "Doe", 222, true, "2018-01-01", "2018-01-01", "Breakfast" } };
	}

	@Test(priority = 3,

			dependsOnMethods = { "API_Post" },

			dataProvider = "puttContent")

	public void API_Put(String firstName, String lastName, Integer price, boolean payment, String checkIn,
			String checkOut, String additional) {

		postData = Booking.updateBooking_Method(firstName, lastName, price, payment, checkIn, checkOut, additional,
				token, id);

		Booking.validateStatusCode_Method(200);

		Booking.validateRequestAndResponseData_Method(postData);

	}

	@Test(priority = 4,

			dependsOnMethods = { "API_Post" })

	public void API_Get() {

		Booking.getBooking_Method(id);

		Booking.validateStatusCode_Method(200);

		Booking.validateRequestAndResponseData_Method(postData);
	}

	@Test(priority = 5,

			dependsOnMethods = { "API_Post" })

	public void API_Delete() {

		Booking.deleteBooking_Method(id);

		Booking.validateStatusCode_Method(201);

		Booking.getBooking_Method(id);

		Booking.validateStatusCode_Method(404);

	}

	@AfterMethod

	public void afterTest(Method method) {

		ANNOTATE(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		ANNOTATE("INFO : Test Case Execution : Test Execution COMPLETED for Test Case - [ " + method.getName() + " ]");
		ANNOTATE(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	@AfterSuite

	public void aftersuite() {

		if (WRITER != null)

			try {

				WRITER.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
	}

	public static void main(String[] args) {

		TestNG testng = new TestNG();

		testng.setTestClasses(new Class[] { test_Runner.class });

		testng.run();
	}

}
