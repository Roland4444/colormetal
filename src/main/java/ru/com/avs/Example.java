package ru.com.avs;

import ch.roland.ModuleGUI;

import java.awt.*;

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
    public JPanel pButton;
    public JLabel lPosition;
    public JLabel lDescription;
    
    public void preperaGUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    	frame = new JFrame();
        frame.setSize(600, 400);
    	tPosition = new JTable();
    	
    	lPosition = new JLabel("Position::");
    	
    	pPosition = new JPanel();
    	pPosition.setLayout(new BorderLayout());
        pPosition.add(lPosition, BorderLayout.NORTH);
        pPosition.add(tPosition, BorderLayout.SOUTH);

    	pDescription = new JPanel();
    	pDescription.setLayout(new BoxLayout(pDescription, BoxLayout.PAGE_AXIS));
    	
    	lDescription = new JLabel("Describe your problem");
    	
        tDescription = new JTextArea();
        
        bRequestHelp = new JButton("Request Help");
        
        bCancel = new JButton("Cancel");
        
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
        
        pButton = new JPanel(new GridLayout(1,2));
        
        pButton.add(bCancel, bRequestHelp);

        pDescription.add(lDescription, tDescription);
        
        main.add(pPosition);
    	//main.add(pDescription);
    	//main.add(pButton);
    	
    	frame.add(main);
    	
    	initListeners();    	
    	
    	frame.setVisible(true);
    }

    public void initListeners() {

    }

    public void initActions() {

    }
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
    	new Example().preperaGUI();
    }
    
}
