package com.android.commands.monkey.scriptevent;

public class LaunchActivity extends IEvent {
	
	private String pkgName;
	private String actName;
	
	public LaunchActivity(String pkgName, String actName) {
		super();
		this.pkgName = pkgName;
		this.actName = actName;
	}
	
	@Override
	public String toString() {
		return "LaunchActivity(" + pkgName + ", " + actName + ")";
	}
	
}
