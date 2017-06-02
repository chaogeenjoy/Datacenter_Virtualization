package embedding;

import java.util.ArrayList;
import java.util.Iterator;

import demand.VDCRequest;
import general.Constant;
import network.Layer;
import network.Node;
/*
 * input��VDC request��һ���������ÿ���������cpu��memory��disk���������Լ��������֮��Ĵ�������
 *      ����ÿ��VM����
 *                  ����PM�Ƿ�ʣ���㹻������㹻�ҵ�ʣ������ٵ��Ǹ���Ȼ��embedding
 */
public class VDCEmbedding {
	/*
	 * �㷨��
	 *    step1��ȡ����һ��VM
	 *    step2��ȡ����һ��PM
	 *    step3���жϸ�PM�Ƿ������VM��������ã�ִ��step4������ת��step5��
	 *    step4������VM��ʱӳ�䵽PM��ռ��PM��Դ��������һ��VM��ת��step3�����û����һ��VM�ˣ�������step6
	 *    step5��ѡ����һ��PM��ת��step3��������������е�PM�ˣ���ǰVMӳ��ʧ�ܣ�����VDCӳ��ʧ�ܡ�
	 *    step6��VDCӳ��ɹ�
	 */
	public boolean vmEmbedding(VDCRequest request,Layer phyLayer){
        ArrayList<Node> PMList=new ArrayList<Node>();
		
		Iterator<String> itr=phyLayer.getNodelist().keySet().iterator();
		while(itr.hasNext()){
			Node node=(Node)(phyLayer.getNodelist().get(itr.next()));
			if(node.getAttribute()==Constant.SERVER){
				PMList.add(node);
			}
		}
		
		
		boolean flag=true;
		Iterator<String> it1=request.getNodelist().keySet().iterator();
		while(it1.hasNext()){
			Node vm=request.getNodelist().get(it1.next());
			int i=0;
			for(;i<PMList.size();i++){
				Node pm=PMList.get(i);
				if((pm.getCpu()>=vm.getCpu())&&(pm.getMemory()>vm.getMemory()&&(pm.getDisk()>vm.getDisk()))){//
					pm.setCpu(pm.getCpu()-vm.getCpu());
					pm.setMemory(pm.getMemory()-vm.getMemory());
					pm.setDisk(pm.getDisk()-vm.getDisk());
					vm.setPM(pm);
					vm.setSuccessEmbed(true);
					break;
				}
			}
			
			if(i>=PMList.size()){//virtual machine embedding failed
				flag=false;
				break;
			}
		}
		
				
		
		if(!flag){//when vm embedding failed , the resource allocated for vm should be released
			Iterator<String> it2=request.getNodelist().keySet().iterator();
			while(it2.hasNext()){
				Node vm=request.getNodelist().get(it2.next());
				if(vm.isSuccessEmbed()){
					Node pm=vm.getPM();
					pm.setCpu(pm.getCpu()+vm.getCpu());
					pm.setMemory(pm.getMemory()+vm.getMemory());
					pm.setDisk(pm.getDisk()+vm.getDisk());
					vm.setPM(null);
					vm.setSuccessEmbed(false);
				}
			}
		}
		
		return flag;
		
	}
	
	
	
	
	
	
	
	/*
	 * ��vmEbedding��ȣ�����������ǽ�vm����cpu�������ݼ�����ʽ��������Ȼ���ٷ���
	 */
	public boolean vmEmbedding_sort(VDCRequest request,Layer phyLayer){
        ArrayList<Node> PMList=new ArrayList<Node>();
        ArrayList<Node> VMList=new ArrayList<Node>();
		
		Iterator<String> itr=phyLayer.getNodelist().keySet().iterator();
		while(itr.hasNext()){
			Node node=(Node)(phyLayer.getNodelist().get(itr.next()));
			if(node.getAttribute()==Constant.SERVER){
				PMList.add(node);
			}
		}
		
		

		for(int j=0;j<request.getNodelist().size();j++){
			
			int minCPU=Integer.MAX_VALUE;
			Node tempVM=null;
			Iterator<String> it0=request.getNodelist().keySet().iterator();
			while(it0.hasNext()){
				Node vm=request.getNodelist().get(it0.next());
				if(!vm.isArrange()){
					if(vm.getCpu()<=minCPU){
						minCPU=vm.getCpu();
						tempVM=vm;
					}
				}		
				
			}
			VMList.add(0,tempVM);
			tempVM.setArrange(true);
		}

		
		boolean flag=true;
		Iterator<Node> it1=VMList.iterator();
		while(it1.hasNext()){
			Node vm=(Node)  it1.next();
			int i=0;
			for(;i<PMList.size();i++){
				Node pm=PMList.get(i);
				if((pm.getCpu()>=vm.getCpu())&&(pm.getMemory()>vm.getMemory()&&(pm.getDisk()>vm.getDisk()))){//
					pm.setCpu(pm.getCpu()-vm.getCpu());
					pm.setMemory(pm.getMemory()-vm.getMemory());
					pm.setDisk(pm.getDisk()-vm.getDisk());
					vm.setPM(pm);
					vm.setSuccessEmbed(true);
					break;
				}
			}
			
			if(i>=PMList.size()){//virtual machine embedding failed
				flag=false;
				break;
			}
		}
		
		
		if(!flag){//when vm embedding failed , the resource allocated for vm should be released
			Iterator<Node> it2=VMList.iterator();
			while(it2.hasNext()){
				Node vm=(Node) it2.next();
				if(vm.isSuccessEmbed()){
					Node pm=vm.getPM();
					pm.setCpu(pm.getCpu()+vm.getCpu());
					pm.setMemory(pm.getMemory()+vm.getMemory());
					pm.setDisk(pm.getDisk()+vm.getDisk());
					vm.setPM(null);
					vm.setSuccessEmbed(false);
				}
			}
		}
		
		return flag;
		
	}
	
	
	
	/*
	 * ����������ӳ�������ȣ����˶�VM�����������⣬���Ҳ�ͬ��VM������ӳ�� ��ͬһ��PM
	 */
	
	public boolean vmEmbeddingsortVM_NonSharePM(VDCRequest request,Layer phyLayer){
        ArrayList<Node> PMList=new ArrayList<Node>();
        ArrayList<Node> VMList=new ArrayList<Node>();
		
        //�ӽڵ��б����ҵ�PM�ŵ�PMList��
		Iterator<String> itr=phyLayer.getNodelist().keySet().iterator();
		while(itr.hasNext()){
			Node node=(Node)(phyLayer.getNodelist().get(itr.next()));
			if(node.getAttribute()==Constant.SERVER){
				PMList.add(node);
			}
		}

		//��vm����CPU�����������򣬷���VMList��
		for(int j=0;j<request.getNodelist().size();j++){
			
			int minCPU=Integer.MAX_VALUE;
			Node tempVM=null;
			Iterator<String> it0=request.getNodelist().keySet().iterator();
			while(it0.hasNext()){
				Node vm=request.getNodelist().get(it0.next());
				if(!vm.isArrange()){
					if(vm.getCpu()<=minCPU){
						minCPU=vm.getCpu();
						tempVM=vm;
					}
				}		
				
			}
			VMList.add(0,tempVM);
			tempVM.setArrange(true);
		}

		
		
		//�ж��Ƿ����ӳ��
		boolean flag=true;
		Iterator<Node> it1=VMList.iterator();
		while(it1.hasNext()){
			Node vm=(Node)  it1.next();
			int i=0;
			for(;i<PMList.size();i++){
				Node pm=PMList.get(i);
				if(pm.isSuccessEmbed())
					continue;
				
				if((pm.getCpu()>=vm.getCpu())&&(pm.getMemory()>vm.getMemory()&&(pm.getDisk()>vm.getDisk()))){//
					pm.setCpu(pm.getCpu()-vm.getCpu());
					pm.setMemory(pm.getMemory()-vm.getMemory());
					pm.setDisk(pm.getDisk()-vm.getDisk());
					pm.setSuccessEmbed(true);//�����־������ʾ��ǰ��PM�Ѿ������ͬһVDC�е�����VM�ˣ�Ҫע�⼰ʱ�ͷŵ�
					vm.setPM(pm);
					vm.setSuccessEmbed(true);					
					break;
				}
			}
			
			if(i>=PMList.size()){//virtual machine embedding failed
				flag=false;
				break;
			}
		}
		
		//��ʱ�ͷŵ���־����������VDCʹ��ʱ���������
		for(Node pm:PMList){
			pm.setSuccessEmbed(false);
		}
		
		if(!flag){//when vm embedding failed , the resource allocated for vm should be released
			Iterator<Node> it2=VMList.iterator();
			while(it2.hasNext()){
				Node vm=(Node) it2.next();
				if(vm.isSuccessEmbed()){
					Node pm=vm.getPM();
					pm.setCpu(pm.getCpu()+vm.getCpu());
					pm.setMemory(pm.getMemory()+vm.getMemory());
					pm.setDisk(pm.getDisk()+vm.getDisk());
					vm.setPM(null);
					vm.setSuccessEmbed(false);
				}
			}
		}
		
		return flag;
		
	}
	
	//���������requestӦ����VMӳ��õ�
	public void vlEmbedding(VDCRequest request,Layer phyLayer){
		 ArrayList<Node> PMList=new ArrayList<Node>();
		 //�ӽڵ��б����ҵ�PM�ŵ�PMList��
		Iterator<String> itr=phyLayer.getNodelist().keySet().iterator();
		while(itr.hasNext()){
			Node node=(Node)(phyLayer.getNodelist().get(itr.next()));
			if(node.getAttribute()==Constant.SERVER){
				PMList.add(node);
			}
		}
		
		
         
	}
}
 