package asmt2.team3.client;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import asmt2.team3.test1.Message;
import asmt2.team3.test1.Node;
import asmt2.team3.test1.dv;

public class Client extends Thread
{
    Set<SelectionKey> dvKeys;
	SocketChannel socketChannel;
    Iterator<SelectionKey> selectedKeysIterator;
    ByteBuffer buffer = ByteBuffer.allocate(5000);
    int bytesRead;
    public void run()
    {
        try {
        		while(true){
        			int readChannel = dv.read.selectNow();
        			dvKeys = dv.read.selectedKeys();
        			selectedKeysIterator = dvKeys.iterator();
        			if(readChannel!=0){
        				while(selectedKeysIterator.hasNext()){
        					SelectionKey dvKey = selectedKeysIterator.next();
        					socketChannel = (SocketChannel)dvKey.channel();
        					try{
        						bytesRead = socketChannel.read(buffer);
        					}catch(IOException ie){
        						selectedKeysIterator.remove();
        						String IP = dv.parseChannelIp(socketChannel);
        						Node node = dv.getNodeByIP(IP);
        						dv.disable(node);
        						System.out.println(IP+" remotely closed the connection!");
        						break;
        					}
        					String message = "";
        					while(bytesRead!=0){
        						buffer.flip();
        						while(buffer.hasRemaining()){
        							message+=((char)buffer.get());
        						}
    							ObjectMapper mapper = new ObjectMapper();
    							Message msg = null;
    							boolean msgRecv = false;
    							int fromID = 0;
    							try{
									msg = mapper.readValue(message,Message.class);
									msgRecv = true;
	    							dv.numberOfPacketsReceived++;
    			        			fromID = msg.getId();
    							}catch(JsonMappingException jme){
    								System.out.println("Server "+dv.parseChannelIp(socketChannel)+" crashed.");
    							}
    			        		Node fromNode = dv.getNodeById(fromID);
    			        		if(msg!=null){
    			        			
    			        			if(msg.getType().equals("update") && msgRecv){
    			        				List<String> recvRT = msg.getRoutingTable();
	        			        		Map<Node,Integer> createdRecvRT = makeRT(recvRT);
	        			        		int currentCost = dv.routingTable.get(fromNode);
	        			        		int updatedCost = createdRecvRT.get(dv.myNode);
	        			        		if(currentCost!=updatedCost){
	        			        			dv.routingTable.put(fromNode,updatedCost);
	        			        		}
    			        			}
	    			        		if(msg.getType().equals("step") && msgRecv) {
	    			        			List<String> recvRT = msg.getRoutingTable();
	        			        		Map<Node,Integer> createdRecvRT = makeRT(recvRT);
	        			        		for(Map.Entry<Node, Integer> entry1 : dv.routingTable.entrySet()){
	        			        			if(entry1.getKey().equals(dv.myNode)){
	        			        				continue;
	        			        			}
	        			        			else{
	        			        				int currentCost = entry1.getValue();
	        			        				int costToReceipient = createdRecvRT.get(dv.myNode); 
	        			        				int costToFinalDest = createdRecvRT.get(entry1.getKey());
        			        					if(costToReceipient+costToFinalDest < currentCost){
        			        					dv.routingTable.put(entry1.getKey(),costToReceipient+costToFinalDest);
        			        					dv.nextHop.put(entry1.getKey(),fromNode);
	        			        			
	        			        			}
	        			        		}
	    			        		}
	        					
	    			        		if(msg.getType().equals("disable") || !msgRecv){
	    			        			dv.routingTable.put(fromNode, Integer.MAX_VALUE-2);
	    			        			System.out.println("Routing Table updated with Server "+fromID+"'s cost set to infinity");
	    			        			if(dv.isNeighbor(fromNode)){
	    			        				for(SocketChannel channel:dv.openChannels){
	    			        					if(fromNode.getIpAddress().equals(dv.parseChannelIp(channel))){
	    			        						try {
	    			        							channel.close();
	    			        						} catch (IOException e) {
	    			        							System.out.println("Cannot close the connection;");
	    			        						}
	    			        						dv.openChannels.remove(channel);
	    			        						break;
	    			        					}
	    			        				}
	    			        				dv.routingTable.put(fromNode, Integer.MAX_VALUE-2);
	    			        				dv.neighbors.remove(fromNode);
	    			        			}
	    			        		}
    			        		}
    			        		if(message.isEmpty()){
    			        			break;
    			        		}
    			        		else{
    			        			System.out.println("Message received from Server "+msg.getId()+" ("+dv.parseChannelIp(socketChannel)+")");
    			        			System.out.println("Current Routing Table:-");
    			        			dv.display();
    			        		}
    			        		buffer.clear();
                    			if(message.trim().isEmpty())
    								bytesRead =0;
    							else{
    								try{
    								bytesRead = socketChannel.read(buffer);
    								}catch(ClosedChannelException cce){
    									System.out.println("Channel closed for communication with Server "+fromID+".");
    								}
    							}
    								
    							bytesRead=0;
    							selectedKeysIterator.remove();
        					}
        				}
        			}
        			}
        		}
        }catch(Exception e) {
        		e.printStackTrace();
        }
        
    }
	private Map<Node, Integer> makeRT(List<String> recvRT) {
		Map<Node,Integer> rTable = new HashMap<Node,Integer>();
		for(String str:recvRT){
			String[] parts = str.split("#");
			int id = Integer.parseInt(parts[0]);
			int cost = Integer.parseInt(parts[1]);
			rTable.put(dv.getNodeById(id), cost);
		}
		return rTable;
	}
 
}









