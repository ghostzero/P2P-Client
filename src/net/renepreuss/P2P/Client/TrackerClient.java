package net.renepreuss.P2P.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TrackerClient {
	private BufferedReader inFromServer;
	private DataOutputStream outToServer;

	public TrackerClient(String trackerIP, int trackerPort){
		try{
			Socket clientSocket = new Socket(trackerIP, trackerPort);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}catch(Exception e){
			
		}
	}
	
	public void registerClient(){
		try {
			outToServer.writeByte(0xB1);
			outToServer.writeUTF("127.0.0.1");
			outToServer.writeInt(Client.getFSPort());
			outToServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PeerClient getRandomPeerByFileHashAndChunkI(String fileHash, int chunkI){
		PeerClient pc = null;
		try {
			outToServer.writeByte(0xC1);
			outToServer.writeUTF(fileHash);
			outToServer.writeInt(chunkI);
			outToServer.flush();
			String peerIP = inFromServer.readLine();
			int peerPort = Integer.parseInt(inFromServer.readLine());
			pc = new PeerClient(peerIP, peerPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pc;
	}
}
