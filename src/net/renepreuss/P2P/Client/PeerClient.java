package net.renepreuss.P2P.Client;

public class PeerClient {
	private String peerIP;
	private int peerPort;

	public PeerClient(String peerIP, int peerPort){
		this.peerIP = peerIP;
		this.peerPort = peerPort;
	}
	
	public String getPeerIP(){
		return peerIP;
	}
	
	public int getPeerPort(){
		return peerPort;
	}
}
