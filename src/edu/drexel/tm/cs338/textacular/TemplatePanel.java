package edu.drexel.tm.cs338.textacular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * The abstract class TemplatePanel.
 * 
 * Represents a panel that contains inputs for the creation of
 * a document type
 * 
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public abstract class TemplatePanel extends JPanel {
	
	/**
	 * The template file directory.
	 */
	protected static final String TEMPLATE_DIR = "res/templates";
	
	/**
	 * The template name.
	 */
	private String templateName;
	
	/**
	 * The template filename.
	 */
	private String templateFilename;
	
	/**
	 * Instantiate a new TemplatePanel.
	 * 
	 * @param templateName the template name
	 */
	public TemplatePanel(String templateName, String templateFilename) {
		this(templateName, templateFilename, new MigLayout());
	}
	
	/**
	 * Instantiate a new TemplatePanel.
	 * 
	 * @param templateName the template name
	 * @param layout the layout
	 */
	public TemplatePanel(String templateName, String templateFilename, MigLayout layout) {
		super(layout);
		
		this.templateName = templateName;
		this.templateFilename = templateFilename;
		
		if (checkTemplateFile()) {
			System.out.println(readTemplateFile());
		}
	}
	
	/**
	 * Get the template name.
	 * 
	 * @return the template name
	 */
	protected String getTemplateName() {
		return templateName;
	}
	
	/**
	 * Get the template filename.
	 * 
	 * @return the template filename
	 */
	protected String getTemplateFilename() {
		return templateFilename;
	}
	
	/**
	 * Determine if the template file exists and is readable.
	 * 
	 * @return if the file exists and is not a directory
	 */
	protected boolean checkTemplateFile() {
		File templateFile = new File(String.format("%s/%s", TEMPLATE_DIR, getTemplateFilename()));
		return templateFile.exists() && !templateFile.isDirectory();
	}
	
	/**
	 * Read the template file.
	 * 
	 * @return the contents of the template file, null on error
	 */
	protected String readTemplateFile() {
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(String.format("%s/%s", TEMPLATE_DIR, getTemplateFilename())));
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
	
	/**
	 * Validate the panel inputs.
	 * 
	 * @return if inputs are filled completely and correctly
	 */
	public abstract boolean checkInputs();
	
	/**
	 * Reset the panel inputs.
	 */
	public abstract void resetInputs();
}
