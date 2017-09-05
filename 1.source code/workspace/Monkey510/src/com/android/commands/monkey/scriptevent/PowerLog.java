package com.android.commands.monkey.scriptevent;

public class PowerLog extends IEvent {
	private String power_log_type = null;
    private String test_case_status = null;
    
	public PowerLog() {
		super();
	}

	public PowerLog(String power_log_type) {
		super();
		this.power_log_type = power_log_type;
	}
	
	public PowerLog(String power_log_type, String test_case_status) {
		super();
		this.power_log_type = power_log_type;
		this.test_case_status = test_case_status;
	}
	
	@Override
	public String toString() {
		if(power_log_type == null){
			return "PowerLog()";
		}
		else if(test_case_status == null){
			return "PowerLog(" + power_log_type + ")";
		}
		return "PowerLog(" + power_log_type + ", " + test_case_status + ")";
	}
     
}
