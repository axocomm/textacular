package edu.drexel.tm.cs338.textacular;

import javax.swing.JFrame;

public class ProjectFrame extends JFrame {
	public ProjectFrame() {
		super("TeXtacular");
		
		add(new LetterPanel());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
	}
}
