package testSet;

import java.util.ArrayList;
import java.util.Random;

import demand.VDCRequest;
import embedding.VDCEmbedding;
import general.Constant;
import network.Layer;

public class VDCTest_Dynamic {
	public static void main(String[] args) {
		double[] erlang={0.2,0.4,0.6,0.8,1.0};
		
		long begin=System.currentTimeMillis();
		for(int i=0;i<erlang.length;i++){
			double er=erlang[i];
			System.out.println("------------erlang load = "+er+"------------------");
			Layer phyLayer=new Layer("PHYLayer", 0, null);
			phyLayer.readTopology("E:\\¶ÁÂÛÎÄ\\DCN\\Topology\\P40T4A4.csv");
			
			
			Random vmNum=new Random(1);
			Random cpu=new Random(2);
			Random memory=new Random(3);
			Random disk=new Random(4);
			Random demand=new Random(5);
			Random arrT=new Random(6);
			double arriveTime=-1.0/er*(Math.log(arrT.nextDouble()));
			VDCRequest vdcRequest=new VDCRequest("VDC0", 0, null, Constant.ARRIVAL,arriveTime, arriveTime-Math.log(arrT.nextDouble()));
			vdcRequest.generateVM(vmNum, cpu, memory, disk);
			vdcRequest.generateNodepairs(demand);
			ArrayList<VDCRequest> requestList=new ArrayList<VDCRequest>();
			requestList.add(vdcRequest);
			
			
			
			
			
			
			
			
			
			int arrivalNUM=0;
			int acceptNUM=0;
			while((arrivalNUM<Constant.SIM_WAN*100)&&(!requestList.isEmpty())){
				VDCRequest currentRequest=requestList.get(0);
				if(currentRequest.getReqType()==Constant.ARRIVAL){
					arrivalNUM++;
					if(arrivalNUM%100000==0){
						System.out.println("NO.:"+arrivalNUM+"\taccept num="+acceptNUM);
					}
					
					VDCRequest departRequest=new VDCRequest(currentRequest.getName(), currentRequest.getIndex(), null, Constant.DEPARTURE,
							currentRequest.getArriveTime(),currentRequest.getDepartTime());
					departRequest.copyRequest(currentRequest);
					insertRequest(requestList, departRequest);
					
					
				
					/*Iterator<String> it=phyLayer.getNodelist().keySet().iterator();
					while(it.hasNext()){
						Node pm=phyLayer.getNodelist().get(it.next());
						if(pm.getAttribute()==Constant.SERVER){
							System.out.println("PM:"+pm.getName()+"\tcpu:"+pm.getCpu());//+"\tmemory:"+(int)pm.getMemory()+"\tdisk:"+(int)pm.getDisk()
						}
						
					}
					
					System.out.println("--------------------------------------");
					
					Iterator<String> it1=currentRequest.getNodelist().keySet().iterator();
					while(it1.hasNext()){
						Node vm=currentRequest.getNodelist().get(it1.next());
						System.out.println("VM:"+vm.getName()+"\tPM:"+vm.getPM().getName());//"\tcpu:"+vm.getCpu()+"\tmemory:"+(int)vm.getMemory()+"\tdisk:"+(int)vm.getDisk()
					}
					*/
					
					
					
					
					VDCEmbedding vdce=new VDCEmbedding();
					if(vdce.vmEmbeddingsortVM_NonSharePM(currentRequest,phyLayer)){
						currentRequest.setFlag(true);
						departRequest.setFlag(true);
						departRequest.copyRequest(currentRequest);
						acceptNUM++;
						/*
						System.out.println("\n\n-----------------------------\n----------------------");
						Iterator<String> it1=currentRequest.getNodelist().keySet().iterator();
						while(it1.hasNext()){
							Node vm=currentRequest.getNodelist().get(it1.next());
							System.out.println("VM:"+vm.getName()+"\tPM:"+vm.getPM().getName());//"\tcpu:"+vm.getCpu()+"\tmemory:"+(int)vm.getMemory()+"\tdisk:"+(int)vm.getDisk()
						}
						System.out.println("\n-----------------------------\n----------------------\n\n\n");*/
					}
					
					
					
					double newArrivalTime=currentRequest.getArriveTime()-1.0/er*Math.log(arrT.nextDouble());
					double newDepartTime=newArrivalTime-Math.log(arrT.nextDouble());
					VDCRequest newRequest=new VDCRequest("VDC"+arrivalNUM, arrivalNUM, null, Constant.ARRIVAL, newArrivalTime, newDepartTime);
					newRequest.generateVM(vmNum, cpu, memory, disk);
					newRequest.generateNodepairs(demand);
					requestList.remove(0);
					insertRequest(requestList, newRequest);
				}else{
					//depart request
					
					if(currentRequest.isFlag()){
						currentRequest.releaseVMResource(phyLayer);					
					}
					requestList.remove(0);
				}
			}
			
			System.out.println("accept ratio:"+((double)acceptNUM/arrivalNUM));
			System.out.println("\n\n");
		}
			
		System.out.println("\nsimulation time:"+(System.currentTimeMillis()-begin)+" (ms)");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void insertRequest(ArrayList<VDCRequest> requestList, VDCRequest request){
		if(requestList.size()==0){
			requestList.add(0, request);
		}else{
			double occurTime;
			if(request.getReqType()==Constant.ARRIVAL)
				occurTime=request.getArriveTime();
			else
				occurTime=request.getDepartTime();
			boolean inserted=false;
			for(int i=0;i<requestList.size();i++){
				VDCRequest currentRequest=requestList.get(i);
				double compareTime;
				if(currentRequest.getReqType()==Constant.ARRIVAL)
					compareTime=currentRequest.getArriveTime();
				else
					compareTime=currentRequest.getDepartTime();
				if(occurTime<compareTime){
					requestList.add(i, request);
					inserted=true;
					break;
				}
			}
			if(!inserted){
				requestList.add(request);
			}
		}
	}
	

}