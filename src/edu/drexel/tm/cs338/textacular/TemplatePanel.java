package edu.drexel.tm.cs338.textacular;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
	protected Map<String, JComponent> inputs;
	
	/**
	 * The template name.
	 */
	private String templateName;
	
	/**
	 * The template filename.
	 */
	private String templateFilename;
	
	/**
	 * The prepared contents.
	 */
	private String preparedContents;
	
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
		
		inputs = new HashMap<String, JComponent>();
		
		prepare();
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
	 * Set the template contents.
	 */
	protected void prepare() {
		if (texHandler.getTemplateContents().length() <= 0) {
			JOptionPane.showMessageDialog(this, "Could not read template contents.",
					"Template Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Add the variables.
	 */
	protected void addVariables() {
		preparedContents = texHandler.getTemplateContents();
		if (preparedContents.length() <= 0) {
			return;
		}
		
		Iterator<Entry<String, JComponent>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, JComponent> pair = it.next();
			String name = pair.getKey();
			JComponent input = pair.getValue();
			String value = getStringValue(input);
			// value = escapeSpecialChars(value);
			preparedContents = preparedContents.replace(String.format(":%s:", name), value);
		}
	}
	
	/**
	 * Prepare the TeXHandler.
	 */
	protected boolean prepareHandler() {
		try {
			texHandler.prepareContents(preparedContents);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Compile the new TeX file.
	 */
	protected boolean compile() {
		try {
			texHandler.compile();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Cleanup.
	 */
	protected void cleanup() {
		texHandler.cleanup();
	}
	
	/**
	 * Get the value of an input.
	 */
	protected String getStringValue(JComponent input) {
		if (input instanceof JTextComponent) {
			return getStringValue((JTextComponent) input);
		} else {
			return "NONE";
		}
	}
	
	/**
	 * Get the value of a text input.
	 */
	protected String getStringValue(JTextComponent input) {
		return input.getText();
	}
	
	/**
	 * Validate the panel inputs.
	 * 
	 * @return if inputs are filled completely and correctly
	 */
	protected boolean checkInputs() {
		Iterator<Entry<String, JComponent>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, JComponent> pair = it.next();
			if (!checkInput(pair.getValue())) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Reset the panel inputs.
	 */
	protected void resetInputs() {
		Iterator<Entry<String, JComponent>> it = inputs.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, JComponent> pair = it.next();
			resetInput(pair.getValue());
		}
	}
	
	/**
	 * Reset an input.
	 */
	protected void resetInput(JComponent input) {
		if (input instanceof JTextComponent) {
			resetInput((JTextComponent) input);
		} else {
			;
		}
	}
	
	/**
	 * Reset a text input.
	 */
	protected void resetInput(JTextComponent input) {
		input.setText("");
	}
	
	/**
	 * Check an input.
	 */
	protected boolean checkInput(JComponent input) {
		if (input instanceof JTextComponent) {
			return checkInput((JTextComponent) input);
		} else if (input instanceof JTable) {
			return checkInput((JTable) input);
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
	
	/**
	 * Check a table input.
	 */
	protected boolean checkInput(JTable input) {
		return input.getModel().getRowCount() > 0;
	}
}
