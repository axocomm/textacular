package edu.drexel.tm.cs338.textacular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class TeXHandler.
 * 
 * Handles validation, generation, and compilation of TeX files
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class TeXHandler {
	
	/**
	 * The directory.
	 */
	private String directory;
	
	/**
	 * The source filename.
	 */
	private String filename;
	
	/**
	 * Instantiate a new TeXHandler.
	 * 
	 * @param directory the directory
	 * @param filename the source filename
	 */
	public TeXHandler(String directory, String filename) {
		this.directory = directory;
		this.filename = filename;
	}
	
	/**
	 * Determine if the source file exists and is readable.
	 * 
	 * @return if the file exists and is not a directory
	 */
	protected boolean checkTemplateFile() {
		File sourceFile = new File(String.format("%s/%s", directory, filename));
		return sourceFile.exists() && !sourceFile.isDirectory();
	}
	
	/**
	 * Read the source file.
	 * 
	 * @return the contents of the source file, null on error
	 */
	protected String readTemplateFile() {
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(String.format("%s/%s", directory, filename)));
			StringBuilder sb = new StringBuilder();
			String line;
			
			while ((line = br.readLine()) != null) {
				sb.append(String.format("%s%n", line));
			}

			br.close();
			
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
