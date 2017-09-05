package com.android.commands.monkey.logger;

import android.os.Environment;

public final class LoggerFactory {
	
	private static final String LOG_PATH_STRING = Environment.getExternalStorageDirectory().getAbsolutePath();
		
	private static Logger scriptLog = new Logger(LOG_PATH_STRING + "/eventScript/script.log");
	private static Logger executeLog = new Logger(LOG_PATH_STRING + "/execute.log");
	
	public static Logger getScriptLogger(){
		return scriptLog;
	}
	
	public static Logger getExecuteLogger(){
		return executeLog;
	}
}
