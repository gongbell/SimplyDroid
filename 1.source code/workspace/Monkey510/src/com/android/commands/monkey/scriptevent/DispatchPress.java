package com.android.commands.monkey.scriptevent;

public class DispatchPress extends IEvent {
	private int mCode;
	
	public DispatchPress(int mCode) {
		super();
		this.mCode = mCode;
	}
	
	@Override
	public String toString() {
		return "DispatchPress(" + mCode + ")";
	}
	
}
