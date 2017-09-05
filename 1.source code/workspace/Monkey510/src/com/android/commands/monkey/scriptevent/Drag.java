package com.android.commands.monkey.scriptevent;

public class Drag extends IEvent {
	private float xStart;
	private float yStart;
	private float xEnd;
	private float yEnd;
	private int stepCount;
	
	public Drag(float xStart, float yStart, float xEnd, float yEnd,
			int stepCount) {
		super();
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.stepCount = stepCount;
	}

	@Override
	public String toString() {
		return "Drag(" + xStart + ", " + yStart + ", " + xEnd + ", " + yEnd
				+ ", " + stepCount + ")";
	}

}
