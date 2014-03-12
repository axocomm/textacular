package edu.drexel.tm.cs338.textacular;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public abstract class TemplatePanel extends JPanel {
	public TemplatePanel() {
		super();
	}
	
	public TemplatePanel(MigLayout layout) {
		super(layout);
	}
	
	public abstract boolean checkInputs();
}
