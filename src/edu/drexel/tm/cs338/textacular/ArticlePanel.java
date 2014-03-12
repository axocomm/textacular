package edu.drexel.tm.cs338.textacular;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The class ArticlePanel.
 * 
 * Displays inputs for components of a basic article
 *
 * @author Trevor Maglione <tm@cs.drexel.edu>
 */
public class ArticlePanel extends TemplatePanel {
	
	/**
	 * The template name.
	 */
	private static final String TEMPLATE_NAME = "Article";
	
	/**
	 * The template filename.
	 */
	private static final String TEMPLATE_FILENAME = "article.tex";
	
	/**
	 * The title label.
	 */
	private JLabel lblTitle;
	
	/**
	 * The author label.
	 */
	private JLabel lblAuthor;
	
	/**
	 * The date label.
	 */
	private JLabel lblDate;
	
	/**
	 * The content label.
	 */
	private JLabel lblContent;
	
	/**
	 * The title text field.
	 */
	private JTextField txtTitle;
	
	/**
	 * The author text field.
	 */
	private JTextField txtAuthor;
	
	/**
	 * The date text field.
	 */
	private JTextField txtDate;
	
	/**
	 * The content text area.
	 */
	private JTextArea txtContent;
	
	/**
	 * Instantiate a new ArticlePanel.
	 */
	public ArticlePanel() {
		super(TEMPLATE_NAME, TEMPLATE_FILENAME);
		
		lblTitle = new JLabel("Title");
		lblAuthor = new JLabel("Author");
		lblDate = new JLabel("Date");
		lblContent = new JLabel("Content");
		
		inputs = new JComponent[] {
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
}
