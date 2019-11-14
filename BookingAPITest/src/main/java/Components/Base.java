package Components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import Components.Utils;
import io.restassured.response.Response;

public class Base {
	
	public static WebDriver webdriver;
	
	public static String BASEURI_AUTH,BASEURI_BOOKING;
	
	public static WebDriverWait wait;
	
	public static BufferedWriter WRITER;
	
	public static StringBuilder RESULTS;
	
	public static Response apiResponse;
	
	public static void readConfigData() {
		
		Utils.configFile();
		
		BASEURI_AUTH = Utils.getConfigValue("baseURI_auth");
		BASEURI_BOOKING = Utils.getConfigValue("baseURI_booking");
		
	}
	
	public static void initialize() {
		
		RESULTS = new StringBuilder();
		
		String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
        File file = new File("./Logs/logfile_"+time+".txt");
        
        try {
        	
			WRITER = new BufferedWriter(new FileWriter(file));
			
			WRITER.write("  " + time + "  :  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- \n");
			WRITER.write("  " + time + "  :  INFO  :  LOG FILE GENERATED \n");
			WRITER.write("  " + time + "  :  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- \n");
			
			
		} catch (IOException e) {
			
			Reporter.log("IOException ERROR occured while creating log file : " + e);
		}

	}
	
	public static void ANNOTATE(String value) {				//Calls WRITE function in Utils class
		
		Utils.WRITE(value);
	}
	
}
