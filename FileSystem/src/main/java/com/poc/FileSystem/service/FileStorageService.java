package com.poc.FileSystem.service;

import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {
	
	
	private int chunkSize = 50000;
	private String operationPath = "D:\\FileRepository\\Operations\\";
	
	
	public void uploadfile(String sourcePath, String destPath, String fileName) {
		
		try {
			
			
			System.out.println("Stage : Fragment To Chunks ");
			List<String> fragmentList  = readAndFragment(sourcePath, fileName );	
			
			System.out.println("Stage : Merge Chunks ");
			mergeParts(fragmentList, destPath, fileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public List<String> readAndFragment(String sourcePath, String fileName) throws IOException {

		
		List<String> fileNamelist = new ArrayList<>();
		String absluteFilePath = sourcePath+fileName;
		File file = new File(absluteFilePath);		
		System.out.println("File AbsPath: " + absluteFilePath);		
		System.out.println("File Exist: " + file.exists());

		if(!file.exists()) {
			return fileNamelist;
		}
		
		int fileSize = (int) file.length();
		System.out.println("Total File Size: " + fileSize);

		int numberOfChunks = 0;
		byte[] temporary = null;

		try {
			InputStream inputStream = null;
			int totalBytesRead = 0;

			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));

				while (totalBytesRead < fileSize) {
					String fragmentPart = "data" + numberOfChunks + ".bin";
					int bytesRemaining = fileSize - totalBytesRead;
					if (bytesRemaining < chunkSize) {

						chunkSize = bytesRemaining;
						System.out.println("chunkSize: " + chunkSize);
					}
					temporary = new byte[chunkSize]; 
					int bytesRead = inputStream.read(temporary, 0, chunkSize);

					if (bytesRead > 0) {
						totalBytesRead += bytesRead;
						numberOfChunks++;
					}

					String absluteFragmentPath = operationPath + fragmentPart;
					write(temporary, absluteFragmentPath);
					fileNamelist.add(absluteFragmentPath);					
				}

			} finally {
				inputStream.close();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return fileNamelist;
	}

	void write(byte[] DataByteArray, String absoluteFileName) {
		try {
			OutputStream output = null;
			try {
				
				System.out.println(" Destination  Path : "+absoluteFileName);
				output = new BufferedOutputStream(new FileOutputStream(absoluteFileName));
				output.write(DataByteArray);
				System.out.println("Writing Process Was Performed Successfully");
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void mergeParts(List<String> nameList, String destinationPath, String fileName) {

		
		String absoluteFileName = destinationPath + fileName;
		File[] file = new File[nameList.size()];
		byte AllFilesContent[] = null;

		int totalLength = 0;
		int fileNumber = nameList.size();
		int fileLength = 0;
		int currentLength = 0;

		for (int i = 0; i < fileNumber; i++) {
			file[i] = new File(nameList.get(i));
			totalLength += file[i].length();
		}

		try {
			
			
			AllFilesContent = new byte[totalLength];
			InputStream inStream = null;

			for (int j = 0; j < fileNumber; j++) {
				inStream = new BufferedInputStream(new FileInputStream(file[j]));
				fileLength = (int) file[j].length();
				inStream.read(AllFilesContent, currentLength, fileLength);
				currentLength += fileLength;
				inStream.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the file " + ioe);
		} finally {
			write(AllFilesContent, absoluteFileName);
		}

		System.out.println("Merge was executed successfully.!");
		

	}
	

	/*public static void main(String args []) throws IOException	{
		String sourceFilePath = "D:\\FileRepository\\SampleFiles\\";
		String destinationFilePath = "D:\\FileRepository\\Destination\\";
		
		String fileName = "Sample_500kb.txt";
		FileStorageService serv = new FileStorageService();		
		serv.uploadfile(sourceFilePath, destinationFilePath,fileName );		
	} */

}

