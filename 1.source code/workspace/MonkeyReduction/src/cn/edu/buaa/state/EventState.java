package cn.edu.buaa.state;

public class EventState {
	//the event and state record should storage the index, event, package name within state, activity within
	//state, and inject code within state information
	private int index;
	private String event;
	private String pkgName;
	private String activity;
	private int injectCode;
		
	public EventState() {
		super();
		this.index = 0;
		this.event = null;
		this.pkgName = null;
		this.activity = null;
		this.injectCode = 0;
	}

	public EventState(int index, String event, String pkgName, String activity, int injectCode, String crash) {
		super();
		this.index = index;
		this.event = event;
		this.pkgName = pkgName;
		this.activity = activity;
		this.injectCode = injectCode;
	}

	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getPkgName() {
		return pkgName;
	}
	
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	
	public String getActivity() {
		return activity;
	}
	
	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getInjectCode() {
		return injectCode;
	}

	public void setInjectCode(int injectCode) {
		this.injectCode = injectCode;
	}
	
}