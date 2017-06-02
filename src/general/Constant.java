package general;

public class Constant {
	public final static int UNVISITED=0;
	public final static int VISITEDONCE=1;
	public final static int VISITEDTWICE=2;
	public final static int UNORDER=0;          //�ڵ�Ի�δ��˳������
	public final static int ORDERED=1;          //�ڵ���Ѿ���˳������
	
	public final static int CPU_PER_PM=8;//CPU��Ŀ
	public final static double DISK_PER_PM=500;//500GӲ��
	public final static double MEMORY_PER_PM=64;//64GB��memory
	public final static char ARRIVAL=0;
	public final static char DEPARTURE=1;
	public final static int SERVER=0;
	public final static int SWITCH=1;
	
	public static final int BANDWIDTH_TIER1 = 1024;//1Gb/s
	public static final int BANDWIDTH_TIER2 = 1024*10;//10Gb/s
	public static final int BANDWIDTH_TIER3 = 1024*10;//10Gb/s
	
	public static final int SIM_WAN = 10000;
}

