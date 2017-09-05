package com.android.commands.monkey.scriptevent;

public class UserWait extends IEvent {
	private long sleeptime;

	public UserWait(long sleeptime) {
		super();
		this.sleeptime = sleeptime;
	}

	@Override
	public String toString() {
		return "UserWait(" + sleeptime + ")";
	}
	
}
