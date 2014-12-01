package net.renepreuss.P2P.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class FileTransferSocket extends Thread{

	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	public FileTransferSocket(Socket client){
		try{
			inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		    outToClient = new DataOutputStream(client.getOutputStream());
		}catch(Exception e){
			System.out.println("FileTransferSocket::init: " + e);
		}
	}
	
	public void run(){
		try{
			String md5FormFile = inFromClient.readLine();
			int chunkI = Integer.parseInt(inFromClient.readLine());
			String filePath = Client.getIndexedFilePathByMD5(md5FormFile);
			if(filePath != null){
				outToClient.writeBytes("OK".toString());
				getChunkFromFile(chunkI, outToClient, filePath);
			}else{
				outToClient.writeBytes("FAIL".toString());
			}
			inFromClient.close();
			outToClient.close();
		}catch(Exception e){
			System.out.println("FileTransferSocket::run(): " + e);
		}
	}
	
	public void getChunkFromFile(int chunkI, DataOutputStream outToClient, String inputFile){
		try{
			FileInputStream in = new FileInputStream(inputFile);
	        final int split_size = 1024 ;
	        byte[] data = new byte[split_size];
	        int numbytes = 0;
	        for(int i = 0; (numbytes = in.read(data)) != -1; i++){
	        	if(chunkI == i){
	        		outToClient.write(data, 0, numbytes);	
	        	}
	        }
	        in.close();
		}catch(Exception e){
			System.out.println("Error FileTransferSocket::getChunk(): " + e);
		}
	}
}
