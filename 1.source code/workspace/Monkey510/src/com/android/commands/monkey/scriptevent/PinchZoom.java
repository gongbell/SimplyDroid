package com.android.commands.monkey.scriptevent;

public class PinchZoom extends IEvent {
	private float pt1xStart;
	private float pt1yStart;
	private float pt1xEnd;
	private float pt1yEnd;

	private float pt2xStart;
	private float pt2yStart;
	private float pt2xEnd;
	private float pt2yEnd;

	private int stepCount;

	public PinchZoom(float pt1xStart, float pt1yStart, float pt1xEnd,
			float pt1yEnd, float pt2xStart, float pt2yStart, float pt2xEnd,
			float pt2yEnd, int stepCount) {
		super();
		this.pt1xStart = pt1xStart;
		this.pt1yStart = pt1yStart;
		this.pt1xEnd = pt1xEnd;
		this.pt1yEnd = pt1yEnd;
		this.pt2xStart = pt2xStart;
		this.pt2yStart = pt2yStart;
		this.pt2xEnd = pt2xEnd;
		this.pt2yEnd = pt2yEnd;
		this.stepCount = stepCount;
	}

	@Override
	public String toString() {
		return "PinchZoom(" + pt1xStart + ", " + pt1yStart + ", " + pt1xEnd
				+ ", " + pt1yEnd + ", " + pt2xStart + ", " + pt2yStart + ", "
				+ pt2xEnd + ", " + pt2yEnd + ", " + stepCount + ")";
	}


}
