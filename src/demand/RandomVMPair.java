package demand;

import java.util.ArrayList;
import java.util.Random;

import network.Node;
import network.NodePair;

/*
 * �����еķ�����Ҫ�Ǹ��������������ڵ�Ե�����
 *             �����ڵ�Ե������ͽڵ��
 */
public class RandomVMPair {

	public void generateVMPair(ArrayList<Node> vmList){
		ArrayList<NodePair> vmPairList=new ArrayList<NodePair>();
		/*
		 * ���Ȳ����ڵ�Ե������������
		 */
		int vmPairNum=new Random(1).nextInt((vmList.size()*(vmList.size()-1)/2)+1);//�ڵ�Ե��������ݽڵ�������������
		
	}
}
