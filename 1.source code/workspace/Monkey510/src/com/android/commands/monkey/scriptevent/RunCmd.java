package com.android.commands.monkey.scriptevent;

public class RunCmd extends IEvent {
	private String cmd;

	public RunCmd(String cmd) {
		super();
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		return "RunCmd(" + cmd + ")";
	}
	
}
