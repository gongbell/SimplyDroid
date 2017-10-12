package com.android.commands.monkey.scriptevent;

public class DispatchPointer extends IEvent {
	private long downTime;
	private long eventTime;
	private int action;
	private float x;
	private float y;
	private float pressure;
	private float size;
	private int metaState;
	private float xPrecision;
	private float yPrecision;
	private int device;
	private int edgeFlags;
	
	public DispatchPointer(long downTime, long eventTime, int action, float x,
			float y, float pressure, float size, int metaState,
			float xPrecision, float yPrecision, int device, int edgeFlags) {
		super();
		this.downTime = downTime;
		this.eventTime = eventTime;
		this.action = action;
		this.x = x;
		this.y = y;
		this.pressure = pressure;
		this.size = size;
		this.metaState = metaState;
		this.xPrecision = xPrecision;
		this.yPrecision = yPrecision;
		this.device = device;
		this.edgeFlags = edgeFlags;
	}

	@Override
	public String toString() {
		return "DispatchPointer(" + downTime + ", " + eventTime + ", " + action
				+ ", " + x + ", " + y + ", " + pressure + ", " + size + ", "
				+ metaState + ", " + xPrecision + ", " + yPrecision + ", "
				+ device + ", " + edgeFlags + ")";
	}
	
}
