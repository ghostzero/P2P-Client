package net.renepreuss.P2P.Client.JsonPatch;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.codec.digest.DigestUtils;

public class PatchGenerator {
	private int partsSize = 0;
	private String fileHash = "";
	public PatchGenerator(String inputFile){
		try{
			FileInputStream in = new FileInputStream(inputFile);
			fileHash = DigestUtils.md5Hex(in);
			
	        final int split_size = 512*1024;
	        byte[] data = new byte[split_size];
	        int numbytes = 0;
	        for(int i = 0; (numbytes = in.read(data)) != -1; i++){
	        	partsSize++;
	        	System.out.println("PartHash (" + i + "): " + DigestUtils.md5Hex(data));
	        }
	        in.close();
	        
	        System.out.println("Parts: " + partsSize);
	        System.out.println("FileHash: " + fileHash);
		}catch(Exception e){
			System.out.println("Error: " + e);
		}
	}
}
