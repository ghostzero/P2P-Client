package net.renepreuss.P2P.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class FileTransferClient {
	private int clientPort;
	private String clientIP;
	private String fileHash;

	public FileTransferClient(String patchFile){
		this.clientIP = "127.0.0.1";
		this.clientPort = 3000;
		
		fileHash = "";
		int chunkITotal = 10;
		
		for(int i = 0; i <= chunkITotal; i++){
			boolean isDownloadDone = false;
			do{
				isDownloadDone = downloadFileChunk(i, "123");
			}while(!isDownloadDone);
		}
	}
	
	private boolean downloadFileChunk(int chunkI, String chunkIHash){
		try{
			boolean downloadStatus = false;
			Socket clientSocket = new Socket(clientIP, clientPort);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeInt(chunkI);
			outToServer.write(fileHash.getBytes());
			String serverResponse = inFromServer.readLine();
			System.out.println("Server Result: " + serverResponse);
			if(serverResponse.equalsIgnoreCase("OK")){
				downloadStatus = true;
			}
			clientSocket.close();
			return downloadStatus;
		}catch(Exception e){
			System.out.println();
			return false;
		}
	}
}
