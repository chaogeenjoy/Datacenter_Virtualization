package elements_DCN;
/*
��Ӧ������㣬server����PM(Physical Machine)
 ��Ӧ����㣬server����VM��Virtual Machine��*/
 
import network.Node;

public class Server extends Node {
	private double storage;
	private int cpu;
	private double disk;
	
	public Server(String name, int index, String comments, double storage, int cpu, double disk) {
		super(name, index, comments);
		this.storage = storage;
		this.cpu = cpu;
		this.disk = disk;
	}
	public double getStorage() {
		return storage;
	}
	public void setStorage(double storage) {
		this.storage = storage;
	}
	public int getCpu() {
		return cpu;
	}
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}
	public double getDisk() {
		return disk;
	}
	public void setDisk(double disk) {
		this.disk = disk;
	}
	
	

}
