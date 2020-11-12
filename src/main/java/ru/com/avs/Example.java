package ru.com.avs;

import ch.roland.ModuleGUI;

import java.awt.GridLayout;

import javax.swing.*;

public class Example extends ModuleGUI {
	public JFrame frame;
	public JTable tPosition;
    public JPanel pPosition;
    public JPanel pDescription;
    public JTextArea tDescription;
    public JButton bRequestHelp;
    public JButton bCancel;
    public JPanel main;
    public JPanel buttonPanel;
    
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    	frame = new JFrame();
    	tPosition = new JTable();
    	pPosition = new JPanel();
    	pPosition.setLayout(new BoxLayout(pPosition, BoxLayout.PAGE_AXIS));

    	pDescription = new JPanel();
    	pPosition.setLayout(new BoxLayout(pPosition, BoxLayout.PAGE_AXIS));
        tDescription = new JTextArea();
        bRequestHelp = new JButton("Request Help");
        bCancel = new JButton("Cancel");
        main = new JPanel();
        buttonPanel = new JPanel(new GridLayout(1,2));
    	
    }

    public void initListeners() {

    }

    public void initActions() {

    }
}
