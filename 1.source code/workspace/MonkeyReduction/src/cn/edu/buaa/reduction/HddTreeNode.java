package cn.edu.buaa.reduction;

import java.util.ArrayList;
import java.util.List;

public class HddTreeNode {
	//Besides fundamental structure of nodes in tree, the hierarchy tree node should also storage the index,
	//level, number of nodes in whole subtree, and the corresponding event and state information
	private int idx;
	private int level;
	private int subtreenodes;
	private String event;
	private String state;
	private HddTreeNode parentNode;
	private List<HddTreeNode> childNodes = new ArrayList<HddTreeNode>();
	
	public HddTreeNode() {
		super();
	}
	
	public HddTreeNode(int idx,int level, String event, String state, HddTreeNode parentNode,	List<HddTreeNode> childNodes) {
		super();
		this.idx = idx;
		this.level = level;
		this.event = event;
		this.state = state;
		this.parentNode = parentNode;
		this.childNodes = childNodes;
		this.setSubtreenodes(0);
	}
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSubtreenodes() {
		return subtreenodes;
	}

	public void setSubtreenodes(int subtreenodes) {
		this.subtreenodes = subtreenodes;
	}

	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public HddTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(HddTreeNode parentNode) {
		this.parentNode = parentNode;
	}
	
	public List<HddTreeNode> getChildNodes() {
		return childNodes;
	}
	
	public void setChildNodes(List<HddTreeNode> childNodes) {
		this.childNodes = childNodes;
	}
	
	public boolean addChildNode(HddTreeNode child){
		return this.childNodes.add(child);
	}
	
	public boolean addChildNode(HddTreeNode root, HddTreeNode child){
		return root.childNodes.add(child);
	}
	
}
