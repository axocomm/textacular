package edu.drexel.tm.cs338.textacular;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public abstract class TemplatePanel extends JPanel {
	private String templateName;
	
	public TemplatePanel(String templateName) {
		this(templateName, new MigLayout());
	}
	
	public TemplatePanel(String templateName, MigLayout layout) {
		super(layout);
		
		this.templateName = templateName;
	}
	
	protected String getTemplateName() {
		return templateName;
	}
	
	public abstract boolean checkInputs();
}
