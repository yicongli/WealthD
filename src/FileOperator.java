import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {

	public static void extractDataToFile () {
		String strPoor="";
		String strMiddle="";
		String strRich="";

		for (wealthRatio item : MainClass.wealthRatioData) {
			strPoor   += item.poor + ",";
			strMiddle += item.middle + ",";
			strRich   += item.rich + ",";
		}

		savetoFile(strPoor.substring(0, strPoor.length()-1), "poor");
		savetoFile(strMiddle.substring(0, strMiddle.length()-1), "middle");
		savetoFile(strRich.substring(0, strRich.length()-1), "rich");
	}
	
	public static void savetoFile(String text, String name) {
		try {
			String fileName = name+".csv";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(text);
			output.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
