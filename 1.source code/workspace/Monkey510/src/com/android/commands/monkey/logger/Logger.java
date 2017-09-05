package com.android.commands.monkey.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.android.commands.monkey.scriptevent.IEvent;

public class Logger {

	private FileWriter writer;
	private File log;

	public Logger(String fileName){
		try {
			log = new File(fileName);
			if(!log.exists()){
				try {
					log.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
			writer = new FileWriter(log, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(IEvent event){	
		write(event.toString());
	}
	
	public void write(String info){
		try {
			writer.write(info + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
