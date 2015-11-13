package stub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class start {

	private static final Logger logger = LogManager.getLogger(start.class);
	
	public static void main(String[] args) {
		Properties config = readConfig("config.ini");
	}

	private static Properties readConfig(String filename){
		Properties props = new Properties();
		Path curDir = Paths.get(System.getProperty("user.dir"));
		File configFilePath = new File(curDir.toFile(), "src/config.ini");
		try{
		FileInputStream configFile = new FileInputStream(configFilePath);
		props.load(configFile);
		}catch (FileNotFoundException e)
		{
			System.out.println("Config file not found in " + configFilePath);
			System.exit(12);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(props.get("inputFolder"));
		return props;
	}	
}
