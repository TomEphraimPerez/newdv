package asmt2.team3.test1;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private String ipAddress;
	private int port;
	private int id;
	private String type;
	public Message(){}
	private List<String> routingTable= new ArrayList<String>();
	public Message(int id, String ipAddress, int port, String type) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.port = port;
		this.type = type;
	} //Message

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getRoutingTable() {
		return routingTable;
	}
	public void setRoutingTable(List<String> routingTable) {
		this.routingTable = routingTable;
	}
} // getId
