package cn.edu.buaa.reduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.w3c.dom.Node;

import cn.edu.buaa.ReductionRunner;
import cn.edu.buaa.command.AdbOperation;
import cn.edu.buaa.state.EventQueueOperation;
import cn.edu.buaa.state.EventState;
import cn.edu.buaa.util.MD5;

public class BalancedHierarchicalDeltaDebugging {
	private static final int TESTCASE_DELTA = 0;
	private static final int TESTCASE_GRADIENT = 1;
	private Set<String> failedSet = new HashSet<String>();
	private List<Integer> minConfig = new ArrayList<Integer>();
	private List<EventState> upSubList = new ArrayList<EventState>();
	private HashMap<Integer, ArrayList<EventState>> downSubListMap = new HashMap<Integer, ArrayList<EventState>>();
	private List<Integer> downSubListGroup = new ArrayList<Integer>();
	
	private List<EventState> eQueue = new ArrayList<EventState>();
	private String crash = null;
	private HddTreeNode root = null;
	private ArrayList<HddTreeNode> nodeList = new ArrayList<HddTreeNode>();
	
	int level;
	int sublistsize;
	int index;
	
	public BalancedHierarchicalDeltaDebugging() {
		index = 0;
	}
	
	public BalancedHierarchicalDeltaDebugging(int idx) {
		index = idx;
	}

	//The method of BHDD reduction entrance
	public void reduce(){
		System.out.println("Trace " + index + " BHDD. Start.");
		long startTime = System.currentTimeMillis();

		//read the log files and extract crash information
		EventQueueOperation eopt = new EventQueueOperation();
		eQueue = eopt.readLog(ReductionRunner.generateOriginPath(index));
		crash = eopt.getCrash(ReductionRunner.generateOriginPath(index) + "execute.log");
		
		if(crash != null && !crash.equals("")){
			root = new HddTreeNode();
			//build the hierarchy tree
			createHddTree(root, eQueue);
			//run the BHDD reduction
			hdd(root);
		}
		else
			System.out.println("Something wrong while getting crash info.");
		
		long endTime = System.currentTimeMillis();
		//calculate the time used while reduction completed
		System.out.println("Trace " + index + " BHDD. Time used : " + (endTime - startTime) + "ms");
		ReductionRunner.calculateTime(endTime - startTime);
		System.out.println("------------------------------------------------");
	}

	//The method to build the hierarchy tree
	private void createHddTree(HddTreeNode root, List<EventState> list){
		root.setIdx(1);
		root.setLevel(0);
		root.setEvent(list.get(0).getEvent());
		root.setState(list.get(0).getActivity());
		root.setParentNode(null);
		nodeList.add(null);
		nodeList.add(root);

		int i;
		HddTreeNode thisNode,lastNode,seekNode;
		lastNode=root;
		for(i=1; i<list.size(); i++)
		{
			//establish new node for each element in event sequence
			thisNode = new HddTreeNode(i+1, -1, list.get(i).getEvent(), list.get(i).getActivity(), null, new ArrayList<HddTreeNode>());
			//search a node having same state information within the route from last node to root node
			for(seekNode=lastNode; ; seekNode=seekNode.getParentNode())
			{
				//searching failed, set new node as a child node of last node
				if(seekNode.getIdx() == 1)
				{
					thisNode.setParentNode(lastNode);
					lastNode.addChildNode(thisNode);
					thisNode.setLevel(lastNode.getLevel() + 1);
					break;
				}
				//searching succeed, set new node as a sibling node of target node
				if(seekNode.getState().equals(thisNode.getState()))
				{
					thisNode.setParentNode(seekNode.getParentNode());
					seekNode.getParentNode().addChildNode(thisNode);
					thisNode.setLevel(seekNode.getLevel());
					break;
				}
			}
			nodeList.add(thisNode);
			lastNode = thisNode;
		}
	}

	//The method to manage the reduction within hierarchy tree
	private void hdd(HddTreeNode root){
		level = 1;

		//tag nodes within level 1
		List<Integer> curNodes = new ArrayList<Integer>();
		curNodes.add(root.getIdx());
		List<Integer> nodes = tagNodes(curNodes);

		//do reduction level by level until there is no node in next level
		while(nodes.size() != 0){
			//do reduction on nodes within this level
			ddmin(nodes);
			//after reduction eliminate useless nodes from hierarchy tree
			pruneHddTree(nodes);
			level++;
			//tag nodes in next level
			nodes = tagNodes(minConfig);
		}
		executeEventList(null);
	}

	//The method to return node index sequence of all the child nodes of given nodes
	private List<Integer> tagNodes(List<Integer> config){
		List<Integer> nodes = new ArrayList<Integer>();
		int i,j;
		for(i=0; i<config.size();i++)
			for(j=0; j<nodeList.get(config.get(i)).getChildNodes().size(); j++)
				nodes.add(nodeList.get(config.get(i)).getChildNodes().get(j).getIdx());
		return nodes;
	}

	//The method to eliminate useless nodes from hierarchy tree
	private void pruneHddTree(List<Integer> nodes){
		List<Integer> temp = new ArrayList<Integer>(nodes);
		//tag useless nodes
		temp.removeAll(minConfig);
		//then remove these nodes as well as their subtree recursively		
		int i,j;
		for(i=0; i<temp.size(); i++)
		{
			j = temp.get(i);
			nodeList.get(j).getParentNode().getChildNodes().remove(nodeList.get(j));
			destroyChildNodes(j);
		}
	}

	//The method to remove a node as well as nodes in its subtree recursively
	private void destroyChildNodes(int idx){
		int i;
		nodeList.get(idx).setParentNode(null);
		for(i=0; i<nodeList.get(idx).getChildNodes().size(); i++)
		{
			destroyChildNodes(nodeList.get(idx).getChildNodes().get(i).getIdx());
		}
		nodeList.get(idx).getChildNodes().clear();
		nodeList.remove(idx);
		nodeList.add(idx, null);
		return;
	}

	//The method to initialize reduction on a given node sequence in hierarchy tree
	private void ddmin(List<Integer> nodes){
		int i;
		//generate event sequence of nodes above the level doing reduction on now
		upSubList = createUpSubList();
		//generate event sequences matching to the subtree of each node within the level doing reduction on now
		downSubListMap.clear();
		for(i=0; i<nodes.size(); i++){
			sublistsize=0;
			downSubListMap.put(nodes.get(i), createDownSubList(nodes.get(i)));
			nodeList.get(nodes.get(i)).setSubtreenodes(sublistsize);
		}
		minConfig = new ArrayList<Integer>(nodes);
		//begin the reduction formally
		ddmin(nodes, 2);
		return;
	}

	//The method to do reduction on a given node sequence
	private void ddmin(List<Integer> nodes, int part){
		System.out.println("EQ: " + nodes.size() + ", partition: " + part);

		//while there is only 1 node in the sequence, stop the reduction
		if(nodes.size() == 1)
			return;
		//ensure that the number of partition would not exceed the number of nodes in the sequence
		if(nodes.size() < part)
			part=nodes.size();
		//divide the nodes into several partitions
		setDownSubListGroup(nodes, part);
		boolean succ = false;

		//first attempt each partition of the sequence
		succ = execDeltaOrGrad(nodes, part, TESTCASE_DELTA);
		if(succ) return;

		//if every attempt failed, then attempt each complement
		else succ = execDeltaOrGrad(nodes, part, TESTCASE_GRADIENT);
		if(succ) return;

		//if still failed, attempt finer partition
		if(!succ && part < nodes.size()) {
			ddmin(nodes, Math.min(nodes.size(), 2 * part));
		}
		return;
	}

	//The method to attempt reduction on a partition or complement
	private boolean execDeltaOrGrad(List<Integer> tmpQueue, int partition, int type){
		List<Integer> currList = tmpQueue;
		List<Integer> attpList = new ArrayList<Integer>();
		boolean succ = false, isCrash = false;
		String sign = null;
		
		//skip those partitions or complements that do not contain last node automatically
		for(int i = ((type == TESTCASE_DELTA)? partition-1: partition-2); i >= ((type == TESTCASE_DELTA)? partition-1:0); i--){
			//generate the event sequence for reduction attempt
			List<EventState> tmp = createEventList(currList, i, type, attpList);

			//skip those event sequences that have been executed and verified as failed attempt
			sign = MD5.getMD5(tmp);
			if(failedSet.contains(sign)) continue;

			//execute the event sequence on testing phone and check whether same crash is reproduced
			isCrash = executeEventList(tmp);
			if(isCrash){
				succ = true;
				//if reduction attempt succeed, record the attempt node sequence
				minConfig = new ArrayList<Integer>(attpList);
				//and call the method of reduction recursively
				if(type == TESTCASE_DELTA)	{
					ddmin(attpList, 2);
				}
				else {
					ddmin(attpList, Math.max(partition - 1, 2));
				}
				break;
			} else {
				if(!failedSet.contains(sign))
					failedSet.add(sign);
			}			
		}
		return succ;
	}

	//The method to generate event sequence through given original node sequence and parameters
	private List<EventState> createEventList(List<Integer> queue, int idx, int type, List<Integer> queuer){
		List<EventState> res = new ArrayList<EventState>(upSubList);
		ArrayList<ArrayList<EventState>> tmp = new ArrayList<ArrayList<EventState>>();
		queuer.clear();
		int i,j,k;
		j = (idx==0? 0: downSubListGroup.get(idx-1)+1);
		k = downSubListGroup.get(idx);

		//for the partition, add to temporary event sequence all events within the event sequences 
		//matching to the subtree of nodes within the given partition
		if(type == TESTCASE_DELTA){
			for(i = j; i <= k; i++){
				tmp.add(downSubListMap.get(queue.get(i)));
				queuer.add(queue.get(i));
			}			
		}
		//for the complement, add to temporary event sequence all events within the event sequences 
		//matching to the subtree of nodes except the given partition
		if(type == TESTCASE_GRADIENT){
			for(i = 0; i < queue.size(); i++){
				if(i < j || i > k){
					tmp.add(downSubListMap.get(queue.get(i)));
					queuer.add(queue.get(i));
				}
			}
		}

		//then orderly insert all events within each temporary event sequence to the event sequence 
		//of nodes above this level to generate the attempt event sequence
		for(i=0, j=0; i<tmp.size(); i++)
		{
			k=tmp.get(i).get(tmp.get(i).size()-1).getIndex();
			while(j<res.size() && res.get(j).getIndex()<k)
				j++;
			res.addAll(j, tmp.get(i));
			j+=tmp.get(i).size();
		}		
		return res;
	}

	//The method to manage the generation of event sequence of nodes above the level now
	private ArrayList<EventState> createUpSubList()
	{
		ArrayList<EventState> list = new ArrayList<EventState>();
		AddUpEventState(list, nodeList.get(1));
		return list; 
	}

	//The method to add events to the event sequence of nodes above the level now recursively
	private void AddUpEventState(ArrayList<EventState> currList, HddTreeNode currNode)
	{
		currList.add(eQueue.get(currNode.getIdx()-1));
		if(level == currNode.getLevel()+1)
			return;
		int i;
		for(i=0; i<currNode.getChildNodes().size(); i++)
			AddUpEventState(currList, currNode.getChildNodes().get(i));
		return;
	}

	//The method to manage the generation of event sequence matching to the subtree of a given node
	private ArrayList<EventState> createDownSubList(int idx)
	{
		ArrayList<EventState> list = new ArrayList<EventState>();
		AddDownEventState(list, nodeList.get(idx));
		return list; 
	}

	//The method to add events to the event sequence matching to the subtree of a given node recursively
	private void AddDownEventState(ArrayList<EventState> currList, HddTreeNode currNode)
	{
		currList.add(eQueue.get(currNode.getIdx()-1));
		sublistsize++;
		int i;
		for(i=0; i<currNode.getChildNodes().size(); i++)
			AddDownEventState(currList, currNode.getChildNodes().get(i));
		return;
	}
	
	//The method to calculate the partition scheme that ensure the total number of nodes within subtrees 
	//of nodes in each partition is as approaching as possible
	private void setDownSubListGroup(List<Integer> nodes, int part)
	{
		int i,l,n=0;
		float avgsize;
		
		//calculate number of nodes in all subtrees
		for(i=0; i<nodes.size(); i++){
			n += nodeList.get(nodes.get(i)).getSubtreenodes();
		}
		avgsize = (float)n / part;
		downSubListGroup.clear();
		
		//add the index of the first node in each partition into a sequence
		for(i=0, l=nodes.size()-part, n=0; i<nodes.size(); i++){
			if(l == 0){
				downSubListGroup.add(i);
				continue;
			}
			n += nodeList.get(nodes.get(i)).getSubtreenodes();
			if(n >= avgsize){
				n = 0;
				downSubListGroup.add(i);
				continue;
			}
			l--;
		}
	}
	
	//The method to execute a event sequence on testing phone and check whether same crash is reproduced
	private boolean executeEventList(List<EventState> list){
		if(list != null)
			printResult(list);

		//execute the event sequence on testing phone
		AdbOperation adb = new AdbOperation();
		adb.execAdbShell(ReductionRunner.generateResultPath(index, 3), ReductionRunner.DEVICE_TMP_PATH, ReductionRunner.DEVICE_MONKEY_PATH, ReductionRunner.DEVICE_ID, ReductionRunner.PACKAGE_NAME, ReductionRunner.THROTTLE_TIME_MS);
		EventQueueOperation eqo = new EventQueueOperation();

		//check whether same crash is reproduced
		String newCrash = eqo.getCrash(ReductionRunner.generateResultPath(index, 3) + "execute_tmp.log");
		if(newCrash != null && newCrash.equals(crash)){
			adb.recordResult(ReductionRunner.generateResultPath(index, 3), ReductionRunner.DEVICE_TMP_PATH, ReductionRunner.DEVICE_ID);
			return true;
		}
		return false;
	}

	//The method to call the conversion from event sequence to script file
	private void printResult(List<EventState> list){
		List<EventState> tmp = new ArrayList<EventState>(list);
		EventQueueOperation eqo = new EventQueueOperation();
		eqo.printScript(tmp, ReductionRunner.generateResultPath(index, 3));
	}
}
