package cn.edu.buaa;

import cn.edu.buaa.reduction.DeltaDebugging;
import cn.edu.buaa.reduction.HierarchicalDeltaDebugging;
import cn.edu.buaa.reduction.BalancedHierarchicalDeltaDebugging;
import cn.edu.buaa.reduction.LocalHierarchicalDeltaDebugging;

public class ReductionRunner {
	
	//The root path of test case files within testing host
	public final static String LOCAL_PATH = "C:\\MonkeyReduction\\";
	
	//The directory of monkey files within testing phone
	public final static String DEVICE_MONKEY_PATH = "/data/local/";
	
	//The root directory of external storage within testing phone
	public final static String DEVICE_TMP_PATH = "/storage/emulated/0/";
	
	//The ID to distinguish testing phones used by ADB Shell
	public final static String DEVICE_ID = "";
	
	//The package name of the application to run test case on	
	public final static String PACKAGE_NAME = "";
	
	//The time interval between events in test case while executed
	public final static int THROTTLE_TIME_MS = 800; 
	
	//The number of crash traces to run reduction on
	public final static int TRACE_NUMBER = 1;
	
	//Choice for algorithm to run the reduction
	public final static boolean DO_DD = true;
	public final static boolean DO_HDD = true;
	public final static boolean DO_IHDD = true;
	public final static boolean DO_LHDD = true;
	
	//Choice for whether utilize extra check in LHDD
	public final static boolean NEED_EXTRACHECK = false;

	//The method to convert time(ms) into minutes and seconds
	public static void calculateTime(long ms){
		long day = ms / (24 * 60 * 60 * 1000);
		ms %= 24 * 60 * 60 * 1000;
		long hour = ms / (60 * 60 * 1000);
		ms %= 60 * 60 * 1000;
		long minute = ms / (60 * 1000);
		ms %= 60 * 1000;
		long second = ms / 1000;
		System.out.println(day + " d " + hour + " h " + minute + " m " + second + " s.");
	}
	
	//The method to get the path of folder origin
	public static String generateOriginPath(int index) {
		return LOCAL_PATH + index +  "\\origin\\";
	}

	//The method to get the path of folder result
	public static String generateResultPath(int index, int type) {
		String path;
		path = LOCAL_PATH + index + "\\";
		if(type == 1)
			path = path + "resultdd\\";
		else if(type == 2)
			path = path + "resulthdd\\";
		else if(type == 3)
			path = path + "resultbhdd\\";
		else if(type == 4)
			path = path + "resultlhdd\\";
		return path;
	}
	
	//The method to start the reduction
	public static void main(String[] args) {
		int i = 1;		
		for(; i <= ReductionRunner.TRACE_NUMBER; i++)
		{
			if(DO_DD)
			{
				DeltaDebugging dd = new DeltaDebugging(i);
				dd.reduce();
			}
			if(DO_HDD)
			{		
				HierarchicalDeltaDebugging hdd = new HierarchicalDeltaDebugging(i);
				hdd.reduce();
			}
			if(DO_IHDD)
			{		
				BalancedHierarchicalDeltaDebugging ihdd = new BalancedHierarchicalDeltaDebugging(i);
				ihdd.reduce();
			}
			if(DO_LHDD)
			{		
				LocalHierarchicalDeltaDebugging lhdd = new LocalHierarchicalDeltaDebugging(i);
				lhdd.reduce();
			}
		}		
	}

}
