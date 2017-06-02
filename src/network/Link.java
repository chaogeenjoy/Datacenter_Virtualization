package network;

import general.CommonObject;
import general.Constant;

public class Link extends CommonObject{
	
	private Layer associatedLayer = null; //the layer that the link belongs to
	private Node nodeA = null; //node A
	private Node nodeB = null; //node B
	private double length = 0; //physical distance of the link
	private double cost = 0;// the cost of the link
	private int status = Constant.UNVISITED;//the visited status	
	private int remainingBandwidth;
	private int bandwidth;	
	
	private int tier;//该链路所处的级别
	public Layer getAssociatedLayer() {
		return associatedLayer;
	}
	public void setAssociatedLayer(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}
	public Node getNodeA() {
		return nodeA;
	}
	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}
	public Node getNodeB() {
		return nodeB;
	}
	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public int getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	
	public int getRemainingBandwidth() {
		return remainingBandwidth;
	}
	public void setRemainingBandwidth(int remainingBandwidth) {
		this.remainingBandwidth = remainingBandwidth;
	}
	
	
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public Link(String name, int index, String comments, Layer associatedLayer,
			Node nodeA, Node nodeB, double length, double cost) {
		super(name, index, comments);
		this.associatedLayer = associatedLayer;
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.length = length;
		this.cost = cost;	 	 
		status = Constant.UNVISITED;
	}

  
	
}

