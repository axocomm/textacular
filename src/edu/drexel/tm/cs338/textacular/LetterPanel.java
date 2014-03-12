package edu.drexel.tm.cs338.textacular;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

public class LetterPanel extends TemplatePanel {
	private JLabel lblSignature;
	private JLabel lblAddress;
	private JLabel lblTo;
	private JLabel lblOpening;
	private JLabel lblContent;
	private JLabel lblClosing;
	
	private JTextComponent[] textComponents;
	
	private JTextField txtSignature;
	private JTextField txtOpening;
	private JTextField txtClosing;
	
	private JTextArea txtAddress;
	private JTextArea txtTo;
	private JTextArea txtContent;
	
	public LetterPanel() {
		super(new MigLayout("wrap 4"));
		
		lblSignature = new JLabel("Signature");
		lblAddress = new JLabel("Address");
		lblTo = new JLabel("To");
		lblOpening = new JLabel("Opening");
		lblContent = new JLabel("Content");
		lblClosing = new JLabel("Closing");
		
		textComponents = new JTextComponent[] {
				txtSignature = new JTextField(20),
				txtOpening = new JTextField(20),
				txtClosing = new JTextField(20),
				txtAddress = new JTextArea(5, 20),
				txtTo = new JTextArea(5, 20),
				txtContent = new JTextArea(10, 40)
		};
		
		add(lblAddress);
		add(new JScrollPane(txtAddress));
		add(lblTo);
		add(new JScrollPane(txtTo), "wrap");
		add(lblOpening);
		add(txtOpening, "wrap");
		add(lblContent);
		add(new JScrollPane(txtContent), "span");
		add(lblClosing);
		add(txtClosing, "wrap");
		add(lblSignature);
		add(txtSignature, "wrap");
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
}
