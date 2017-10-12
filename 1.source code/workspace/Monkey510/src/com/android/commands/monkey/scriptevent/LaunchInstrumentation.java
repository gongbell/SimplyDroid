package com.android.commands.monkey.scriptevent;

public class LaunchInstrumentation extends IEvent {
	private String test_name;
    private String runner_name;
    
	public LaunchInstrumentation(String test_name, String runner_name) {
		super();
		this.test_name = test_name;
		this.runner_name = runner_name;
	}
	
	@Override
	public String toString() {
		return "LaunchInstrumentation(" + test_name + ", " + runner_name + ")";
	}
    
}
