package com.android.commands.monkey.scriptevent;

public class PressAndHold extends IEvent {
	private float x;
	private float y;
	private long pressDuration;
	
	public PressAndHold(float x, float y, long pressDuration) {
		super();
		this.x = x;
		this.y = y;
		this.pressDuration = pressDuration;
	}

	@Override
	public String toString() {
		return "PressAndHold(" + x + ", " + y + ", " + pressDuration + ")";
	}

}
