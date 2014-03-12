package edu.drexel.tm.cs338.textacular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

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
	 * The inputs.
	 */
	protected JComponent[] inputs;
	
	/**
	 * The template name.
	 */
	private String templateName;
	
	/**
	 * The template filename.
	 */
	private String templateFilename;
	
	/**
	 * The TeX handler.
	 */
	private TeXHandler texHandler;
	
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
		
		texHandler = new TeXHandler(TEMPLATE_DIR, templateFilename);
		
		if (texHandler.checkTemplateFile()) {
			System.out.println(texHandler.readTemplateFile());
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
	 * Validate the panel inputs.
	 * 
	 * @return if inputs are filled completely and correctly
	 */
	protected boolean checkInputs() {
		for (JComponent input : inputs) {
			if (!checkInput(input)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Reset the panel inputs.
	 */
	protected void resetInputs() {
		
	}
	
	/**
	 * Check an input.
	 */
	protected boolean checkInput(JComponent input) {
		if (input instanceof JTextComponent) {
			return checkInput((JTextComponent) input);
		} else {
			return false;
		}
	}
	
	/**
	 * Check a text input.
	 */
	protected boolean checkInput(JTextComponent input) {
		return input.getText().length() > 0;
	}
}
