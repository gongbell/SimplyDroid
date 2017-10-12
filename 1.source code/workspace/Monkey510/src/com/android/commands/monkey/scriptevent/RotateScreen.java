package com.android.commands.monkey.scriptevent;

public class RotateScreen  extends IEvent {
	private int rotationDegree;
    private int persist;
    
	public RotateScreen(int rotationDegree, int persist) {
		super();
		this.rotationDegree = rotationDegree;
		this.persist = persist;
	}

	@Override
	public String toString() {
		return "RotateScreen(" + rotationDegree + ", " + persist + ")";
	}
    
}
