package edu.drexel.tm.cs338.textacular;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InvoicePanel extends TemplatePanel {
	
	private static final String TEMPLATE_NAME = "Invoice";
	private static final String TEMPLATE_FILENAME = "invoice.tex";
	
	private JLabel lblCompany;
	private JLabel lblAddress;
	private JLabel lblTo;
	private JLabel lblToAddress;
	private JLabel lblDate;
	private JLabel lblRate;
	private JLabel lblEntries;
	
	private JTextField txtCompany;
	private JTextField txtTo;
	private JTextField txtDate;
	private JTextField txtRate;
	
	private JTextArea txtAddress;
	private JTextArea txtToAddress;
	
	private JTable tblEntries;
	
	public InvoicePanel() {
		super(TEMPLATE_NAME, TEMPLATE_FILENAME);
		
		lblCompany = new JLabel("Company");
		lblAddress = new JLabel("Address");
		lblTo = new JLabel("To");
		lblToAddress = new JLabel("To");
		lblDate = new JLabel("Date");
		lblRate = new JLabel("Rate");
		lblEntries = new JLabel("Entries");
		
		inputs.put("company", txtCompany = new JTextField(20));
		inputs.put("address", txtAddress = new JTextArea(5, 20));
		inputs.put("to", txtTo = new JTextField(20));
		inputs.put("to-address", txtToAddress = new JTextArea(5, 20));
		inputs.put("date", txtDate = new JTextField(20));
		inputs.put("rate", txtRate = new JTextField(20));
		inputs.put("entries", tblEntries = new JTable());
		
		add(lblCompany);
		add(txtCompany);
		add(lblAddress);
		add(new JScrollPane(txtAddress), "wrap");
		add(lblTo);
		add(txtTo);
		add(lblToAddress);
		add(new JScrollPane(txtToAddress), "wrap");
		add(lblDate);
		add(txtDate);
		add(lblRate);
		add(txtRate, "wrap");
		add(lblEntries);
		add(tblEntries);
	}
}
