package edu.drexel.tm.cs338.textacular;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class ArticlePanel extends TemplatePanel {
	
	private JLabel lblTitle;
	private JLabel lblAuthor;
	private JLabel lblDate;
	private JLabel lblContent;
	
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtDate;
	
	private JTextArea txtContent;
	
	private JTextComponent[] textComponents;

	public ArticlePanel() {
		super("Article");
		
		lblTitle = new JLabel("Title");
		lblAuthor = new JLabel("Author");
		lblDate = new JLabel("Date");
		lblContent = new JLabel("Content");
		
		textComponents = new JTextComponent[] {
				txtTitle = new JTextField(20),
				txtAuthor = new JTextField(20),
				txtDate = new JTextField(20),
				txtContent = new JTextArea(10, 40)
		};
		
		add(lblTitle);
		add(txtTitle, "wrap");
		add(lblAuthor);
		add(txtAuthor, "wrap");
		add(lblDate);
		add(txtDate, "wrap");
		add(lblContent);
		add(new JScrollPane(txtContent), "wrap");
	}

	@Override
	public boolean checkInputs() {
		for (JTextComponent textComponent : textComponents) {
			if (textComponent.getText().length() <= 0) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void resetInputs() {
		for (JTextComponent textComponent : textComponents) {
			textComponent.setText("");
		}
	}
}
