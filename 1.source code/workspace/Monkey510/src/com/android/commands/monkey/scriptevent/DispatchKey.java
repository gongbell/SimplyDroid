package com.android.commands.monkey.scriptevent;

public class DispatchKey extends IEvent {
	
	private long mDownTime;
	private long mEventTime;
	private int mAction;
	private int mCode;
	private int mRepeat;
	private int mMetaState;
	private int mDevice;
	private int mScancode;

	public DispatchKey(long mDownTime, long mEventTime, int mAction, int mCode,
			int mRepeat, int mMetaState, int mDevice, int mScancode) {
		super();
		this.mDownTime = mDownTime;
		this.mEventTime = mEventTime;
		this.mAction = mAction;
		this.mCode = mCode;
		this.mRepeat = mRepeat;
		this.mMetaState = mMetaState;
		this.mDevice = mDevice;
		this.mScancode = mScancode;
	}
		
	@Override
	public String toString() {
		return "DispatchKey(" + mDownTime + ", " + mEventTime + ", " + mAction
				+ ", " + mCode + ", " + mRepeat + ", " + mMetaState + ", "
				+ mDevice + ", " + mScancode + ")";
	}

}
