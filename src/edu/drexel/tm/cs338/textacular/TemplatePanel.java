package edu.drexel.tm.cs338.textacular;

import java.io.File;

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
		
		System.out.println(checkTemplateFile());
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
	 */
	private boolean checkTemplateFile() {
		File templateFile = new File(String.format("%s/%s", TEMPLATE_DIR, getTemplateFilename()));
		return templateFile.exists() && !templateFile.isDirectory();
	}
	
	/**
	 * Read the template file.
	 */
	
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
