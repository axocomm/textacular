package edu.drexel.tm.cs338.textacular;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * The class TeXHandler.
 * 
 * Handles validation, generation, and compilation of TeX files
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class TeXHandler {
	
	private static final String PREFIX = "textacular";
	
	private static final String JOBNAME = "res/output/output";
	
	/**
	 * The directory.
	 */
	private String directory;
	
	/**
	 * The source filename.
	 */
	private String filename;
	
	/**
	 * The template file contents.
	 */
	private String templateContents;
	
	/**
	 * The temporary TeX file.
	 */
	private File texFile;
	
	/**
	 * Instantiate a new TeXHandler.
	 * 
	 * @param directory the directory
	 * @param filename the source filename
	 */
	public TeXHandler(String directory, String filename) {
		this.directory = directory;
		this.filename = filename;
		
		templateContents = checkTemplateFile() ? readTemplateFile() : "";
		texFile = null;
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
	 * Get the template contents.
	 * 
	 * @return the template contents
	 */
	protected String getTemplateContents() {
		return templateContents;
	}
	
	/**
	 * Prepare the new TeX file.
	 * 
	 * @param newContents the contents with input variables
	 * @throws IOException 
	 */
	protected void prepareContents(String newContents) throws IOException {
		if (texFile == null) {
			texFile = File.createTempFile(PREFIX, ".tex");
			texFile.deleteOnExit();
		}
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(texFile)));
		bw.write(newContents);
		bw.close();
	}
	
	/**
	 * Compile the TeX file.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	protected void compile() throws IOException, InterruptedException {
		System.out.println(texFile.getAbsolutePath());
		Process p = Runtime.getRuntime().exec(String.format("latexmk -gg -pdf -jobname=%s %s", JOBNAME, texFile.getAbsolutePath()));
		p.waitFor();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
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
