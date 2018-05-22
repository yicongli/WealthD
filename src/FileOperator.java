/**
 * The class for outputting the wealth distribution to local file.
 *
 * It is responsible for:
 *  - Convert person wealth number in each tick into string
 *  - Save string into local file
 *
 * @author Yicong   Li      ID:923764 2018-05-18
 * @author Savan    Kanabar ID:965371 2018-05-18
 * @author Emmanuel Mogbeyi ID:854106 2018-05-18
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {

	/*
	 * Extract poor/middle/rich person number from wealthRatioData
	 * and save to local files respectively 
	 */
	public static void extractDataToFile () {
		String strPoor 	 = "";	// string for storing poor person number
		String strMiddle = "";	// string for storing middle person number
		String strRich	 = "";	// string for storing rich person number

		// extract data from cache and save to string
		for (wealthRatio item : MainClass.wealthDistributionData) {
			strPoor   += item.poor + ",";
			strMiddle += item.middle + ",";
			strRich   += item.rich + ",";
		}

		// save to local file respectively
		savetoFile(strPoor.substring(0, strPoor.length()-1), "poor");
		savetoFile(strMiddle.substring(0, strMiddle.length()-1), "middle");
		savetoFile(strRich.substring(0, strRich.length()-1), "rich");
	}
	
	/*
	 * save text into local file 'name'.csv
	 * @param text	 	  poor/middle/rich person numbers of ticks
	 * @param fileNameStr file name
	 */
	public static void savetoFile(String text, String fileNameStr) {
		try {
			String fileName = fileNameStr + ".csv";
			File file = new File(fileName);
			// if file does not exist, create new file
			if (!file.exists()) {
				file.createNewFile();
			}
			
			// write string to local file
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(text);
			output.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
