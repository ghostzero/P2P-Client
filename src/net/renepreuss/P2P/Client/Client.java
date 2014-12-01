package net.renepreuss.P2P.Client;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;

public class Client {
	private static File shareDir;
	private static File chainFiles;
	private static Random r;
	private static TrackerClient trackerClient;
	private static int randomFSPort;
	private static String trackerIP;
	private static int trackerPort;

	public static void main(String[] args) {
		try{
			trackerIP = "127.0.0.1";
			trackerPort = 3000;
			r = new Random();
			shareDir = new File("C:/ShareFolder"); //Directory with all Files to share
			chainFiles = new File("C:/ShareFolder/chainFiles"); //Directory with downloaded parts.
			if(!chainFiles.exists()){
				chainFiles.mkdirs();
			}
			randomFSPort = 3010+r.nextInt(100);
			System.out.println("Create fs server on port: " + randomFSPort);
			ServerSocket fsServer = new ServerSocket(randomFSPort);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
					    try {
							FileTransferSocket fts = new FileTransferSocket(fsServer.accept());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
			System.out.println("Connect to tracker: " + trackerIP + ":" + trackerPort);
			trackerClient = new TrackerClient(trackerIP, trackerPort);
			trackerClient.registerClient();
			System.out.print("Enter your sentence: ");
		    Scanner sc = new Scanner(System.in);
		
		    while(sc.hasNext()) {
		        String s1 = sc.next();
		        if(s1.equals("exit")) {
		            
		        }
		    }
		      
		}catch(Exception e){
			
		}
		
	}
	
	public static File getShareDir(){
		return shareDir;
	}

	public static String getIndexedFilePathByMD5(String md5FormFile) {
		return null;
	}

	public static int getFSPort() {
		return randomFSPort;
	}
}
