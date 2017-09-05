package com.android.commands.monkey.scriptevent;

public class Tap extends IEvent {
	private float x;
	private float y;
	private long tapDuration = 0;
	
	public Tap(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Tap(float x, float y, long tapDuration) {
		super();
		this.x = x;
		this.y = y;
		this.tapDuration = tapDuration;
	}

	@Override
	public String toString() {
		return "Tap(" + x + ", " + y + ", " + tapDuration + ")";
	}
	
	
}
