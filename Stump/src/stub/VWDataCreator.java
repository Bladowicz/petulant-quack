package stub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VWDataCreator implements Runnable {
	private Thread t;
	private String threadName;
	private int interval;
	private File locationDir;
	static private Random generator = new Random();
	
	private static final Logger logger = LogManager.getLogger(VWDataCreator.class);
	
	VWDataCreator(String name, int waitTime, File location){
		threadName = name;
		interval = waitTime;
		locationDir = location;
		logger.info("Creating thread " + threadName);
	}

	@Override
	public void run() {
		logger.info(String.format("[%s] Running.", threadName));
		try{
			for(int i = 4; i > 0; i--){
				File fout = new File(locationDir, String.format("data%d.vw", 1400000 + generator.nextInt(100)*100));
				try {
					FileOutputStream fos = new FileOutputStream(fout);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
					for (int j = 0; j < 10; j++) {
						bw.write("something");
						bw.newLine();
					}
				 
					bw.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				logger.info(String.format("Thread %s says counts down to %d %d", threadName, i, generator.nextInt(100)));
				Thread.sleep(interval);
			}
			
		}catch(InterruptedException e){
			logger.warn(String.format("Thread %s was interupted.", threadName));
		}
		logger.info(String.format("Thread %s says bye", threadName));
	}
	
	public void start()
	{
	if(t == null)
	{
		t = new Thread(this, threadName);
		t.start();
		
	}
	
		
	}

}
