package edu.drexel.tm.cs338.textacular;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

public class ProjectFrame extends JFrame {
	private JMenuBar menuBar;
	
	private JMenu mainMenu;
	
	private JMenuItem itmQuit;
	
	private JTabbedPane tabbedPane;
	
	private JPanel buttonsPanel;
	
	private JButton btnCompile;
	private JButton btnClear;
	private JButton btnOptions;
	
	private TemplatePanel[] panels = { new LetterPanel() };
	
	public ProjectFrame() {
		super("TeXtacular");
		
		createMenuBar();
		setJMenuBar(menuBar);

		tabbedPane = new JTabbedPane();
		addTabPanels();
		
		btnCompile = new JButton("Compile");
		btnClear = new JButton("Clear");
		btnOptions = new JButton("Options");
		
		buttonsPanel = new JPanel(new MigLayout());
		buttonsPanel.add(btnCompile);
		buttonsPanel.add(btnClear);
		buttonsPanel.add(btnOptions, "gapleft 30");
		
		add(tabbedPane);
		add(buttonsPanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
	}
	
	private void createMenuBar() {
		menuBar = new JMenuBar();
		mainMenu = new JMenu("File");
		menuBar.add(mainMenu);
		
		itmQuit = new JMenuItem("Quit", KeyEvent.VK_Q);
		mainMenu.add(itmQuit);
	}
	
	private void addTabPanels() {
		for (TemplatePanel panel : panels) {
			tabbedPane.add(panel.getTemplateName(), panel);
		}
	}
}
