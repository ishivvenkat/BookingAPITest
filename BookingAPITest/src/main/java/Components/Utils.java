package Components;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.testng.Reporter;


public class Utils extends Base {
	
	public static Properties envVariables;
	
	public static void configFile() {
		
		try {
				envVariables = new Properties();
				
				envVariables.load(new FileReader("./Properties/Configurations.properties"));
				
				ANNOTATE("INFO  :  Loading configuration data is completed");
				ANNOTATE("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			} catch (FileNotFoundException e) {
				
				Reporter.log("FileNotFoundException ERROR occured while loading properties file : " + e);
				
			} catch (IOException e) {
				
				Reporter.log("IOException ERROR occured while loading properties file : " + e);
		}

	}
	
	public static String getConfigValue(String Key) {
		
		return envVariables.getProperty(Key);
		
	}

	public static Object[][] getCsvValue(String LOGIN_CSV_FILEPATH) throws IOException {
		

		File file = new File(LOGIN_CSV_FILEPATH);
		
		Path path = file.toPath();
	
		long lineCount = Files.lines(path).count();
	
		if (!file.exists()) {
			
			ANNOTATE("FAIL  :  CSV file data fetch unsuccessfull : FILEPATH - " + LOGIN_CSV_FILEPATH);
		}
		BufferedReader bufRdr;
		
		bufRdr = new BufferedReader(new FileReader(file));
		
		String line = null;
	
		int col = 0;
	
		line = bufRdr.readLine();
		
		String columnCount[] = line.split(",");
		
		int colCount = columnCount.length;
	
		Object csvValues[][] = new Object[(int) lineCount - 1][colCount];
	
		while ((line = bufRdr.readLine()) != null) {
	
			String fields[] = line.split(",");
			
			for(int index=0;index<fields.length;index++) {
				
				csvValues[col][index] = fields[index].toString();
				
			}
	
			col++;
		}
		
		bufRdr.close();
		
		ANNOTATE("INFO  :  Fetching Test Data from CSV file is completed");
		
		ANNOTATE("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		return csvValues;
 
	}
	
	
	public static void WRITE(String value) {						//Function for appending execution data into log files, testNG reporters
		
		try {
			
			String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			
			Reporter.log(" " + value + "\n");
			
			RESULTS.append("  " + time + "  :  " + value + "\n");
			
			WRITER.append(RESULTS.toString());
			
			RESULTS.delete(0, RESULTS.length());	
			
		} catch (IOException e) {
			
			Reporter.log("IOException ERROR occured while writing log data : " + e);
		}
	}	
}
