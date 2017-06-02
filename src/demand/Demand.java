package demand;

import java.util.Random;

/*
 * ����ÿ��VM��cpu, 
 */
public class Demand {
	public static int CPUDemand(Random cpu){
		return (int)(cpu.nextInt(9));//0~8֮���CPU
	}
	
	public static double MemoryDemand(Random memory){
		return memory.nextDouble()*21+10;//0~64GB֮��
	}
	public static double DiskDemand(Random disk){
		return disk.nextDouble()*100;//0~100GB֮��
	}
	
	public static int generateTrafficDemand(Random demand){
		
	    return demand.nextInt(81)+10;//10~80Mbps
	}
	
	public static int VMNumDeman(Random vmNum){
		return vmNum.nextInt(21)+10;//4��16
	}
}
