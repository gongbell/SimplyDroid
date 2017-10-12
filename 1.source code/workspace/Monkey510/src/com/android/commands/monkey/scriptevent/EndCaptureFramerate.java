package com.android.commands.monkey.scriptevent;

public class EndCaptureFramerate extends IEvent {
	private String input;

	public EndCaptureFramerate(String input) {
		super();
		this.input = input;
	}

	@Override
	public String toString() {
		return "EndCaptureFramerate(" + input + ")";
	}

}
