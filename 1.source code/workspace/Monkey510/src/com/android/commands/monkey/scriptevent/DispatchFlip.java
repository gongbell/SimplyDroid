package com.android.commands.monkey.scriptevent;

public class DispatchFlip extends IEvent {
	private boolean keyboardOpen;

	public DispatchFlip(boolean keyboardOpen) {
		super();
		this.keyboardOpen = keyboardOpen;
	}

	@Override
	public String toString() {
		return "DispatchFlip(" + keyboardOpen + ")";
	}
	
	
}
