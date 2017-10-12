package cn.edu.buaa.command;

import java.util.ArrayList;
import java.util.List;

public class AdbOperation {
	
	//The method to complete the execution of a reduction attempt, whose main steps consist of deleting old 
	//files, pushing script file, running monkey, and pulling log file
	public void execAdbShell(String resultPath, String devicePath, String deviceMonkey, String deviceId, String packageName, int throttle){
		List<String> commands = new ArrayList<String>();
		commands.add(adbDeleteCommand(devicePath, deviceId));
		commands.add(adbPushCommand(resultPath, devicePath, deviceId));
		commands.add(adbMonkeyCommand(deviceMonkey, devicePath, packageName, throttle, deviceId));
		commands.add("adb -s " + deviceId + " shell am force-stop " + packageName);
		commands.add(adbPullCommand(resultPath, devicePath, deviceId));
		commands.add("adb -s " + deviceId + " shell rm " + devicePath + "eventScript/*.log");
		execAdbCommands(commands);
	}
	
	//The method to record a set of log files script.log and execute.log that belong to a succeed reduction
	public void recordResult(String resultPath, String devicePath, String deviceId){
		List<String> commands = new ArrayList<String>();
		commands.add("adb -s " + deviceId + " pull " + devicePath + "execute.log " + resultPath + "execute.log");
		commands.add("adb -s " + deviceId + " pull " + devicePath + "script.log " + resultPath + "script.log");
		execAdbCommands(commands);
	}
	
	//The method to generate a push command
	private String adbPushCommand(String resultPath, String devicePath, String deviceId){
		return "adb -s " + deviceId + " push " + resultPath + "script_tmp.log" + " " + devicePath + "script.log ";
	}

	//The method to generate a delete command
	private String adbDeleteCommand(String devicePath, String deviceId){
		return "adb -s " + deviceId + " shell rm " + devicePath + "*.log";
	}
	
	//The method to generate a monkey command
	private String adbMonkeyCommand(String deviceMonkey, String devicePath, String packageName, int throttle, String deviceId){
		return "adb -s " + deviceId + " shell " + deviceMonkey + "monkey -p " + packageName + " -f " + devicePath + "script.log "
				+ "  --throttle " + throttle + " 1";
	}

	//The method to generate a pull command
	private String adbPullCommand(String resultPath, String devicePath, String deviceId){
		return "adb -s " + deviceId + " pull " + devicePath + "execute.log" + " " + resultPath + "execute_tmp.log ";
	}

	//The method to execute commands in order through Adb Shell
	private static void execAdbCommands(List<String> commands){
		try {
			for(String cmd: commands){
				Process pro = Runtime.getRuntime().exec(cmd);
		        pro.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
