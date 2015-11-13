package stub;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class start {

	private static final Logger logger = LogManager.getLogger(start.class);
	
	public static void main(String[] args) {
		Properties config = readConfig("config.ini");
		File dirName = mkdir(new File(config.getProperty("inputFolder")));
		
		runWorkers(Integer.parseInt(config.get("workersCount").toString()), dirName);

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

	public static File mkdir(File dirName){
		if(!dirName.exists()){
			logger.warn("VW folder is not there : " + dirName.toString());
			boolean result = false;
			   try{
			        dirName.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        logger.fatal("Don't have permission to operate on VW folder");
			        System.exit(2);
			    }    
			if(result){
				logger.info("Folder successfully created.");
			}
		}
		return dirName;
	}

	public static void runWorkers(int workerCount, File location)
	{
		List <VWDataCreator> workers = new ArrayList<>();
		for(int i = 0; i < workerCount; i++){
			workers.add(new VWDataCreator(String.format("Worker %d", i), i*250, location));
		}
		for(int i = 0; i < workerCount; i++){
			workers.get(i).start();
		}
	}
	
}
