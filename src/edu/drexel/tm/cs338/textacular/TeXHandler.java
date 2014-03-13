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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class TeXHandler.
 * 
 * Handles validation, generation, and compilation of TeX files
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class TeXHandler {
	
	/**
	 * The temp file prefix.
	 */
	private static final String PREFIX = "textacular";
	
	/**
	 * The output directory.
	 */
	private static final String OUTDIR = "res/output";
	
	/**
	 * The job name for latexmk.
	 */
	private static final String JOBNAME = "output";
	
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
		prepareOutputDir();
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
	 * Prepare the output directory.
	 */
	protected void prepareOutputDir() {
		File outputDir = new File(OUTDIR);
		if (!(outputDir.exists() && outputDir.isDirectory())) {
			outputDir.mkdir();
		}
	}
	
	/**
	 * Escape special characters in input.
	 * 
	 * @param str the input String
	 * @return an escaped String or the original if an exception occurs
	 */
	protected String escapeSpecialChars(String str) {
		String specialChars = "([#\\$\\%\\^&_\\{\\}~])";
		Pattern pattern = Pattern.compile(specialChars);
		try {
			Matcher matcher = pattern.matcher(str);
			String escaped = matcher.replaceAll("\\\\$1");
			return escaped;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * Compile the TeX file.
	 * 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	protected void compile() throws IOException, InterruptedException {
		System.out.println(texFile.getAbsolutePath());
		Process p = Runtime.getRuntime().exec(String.format("/bin/bash res/bin/compile %s/%s %s",
				OUTDIR, JOBNAME, texFile.getAbsolutePath()));
		p.waitFor();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}
	
	/**
	 * Remove compiled and intermediate files.
	 */
	protected void cleanup() {
		String[] extensions = { "aux", "fdb_latexmk", "log", "pdf" };
		for (String extension : extensions) {
			File f = new File(String.format("%s/%s.%s", OUTDIR, JOBNAME, extension));
			System.out.printf("Deleting %s\n", f.getAbsoluteFile());
			if (f.exists() && !f.isDirectory()) {
				f.delete();
			}
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
