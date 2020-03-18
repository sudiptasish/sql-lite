package com.sc.hm.sqll.ui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.sc.hm.sqll.event.ExecutionListener;
import com.sc.hm.sqll.event.PlanOptionListener;

public class ControlPanel extends JPanel {

	private final int spacing = 10;
	
	private final UI ui;
	private final int width;
	private final int height;
	private final int splitWidth;
	
	private JButton executeButton = null;
	private JButton commitButton = null;
	private JButton rollbackButton = null;
	private JRadioButton basicButton = null;
	private JRadioButton typicalButton = null;
	private JRadioButton allButton = null;
	private JButton planButton = null;
	
	public ControlPanel(UI ui, int width, int height, int splitWidth) {
		this.ui = ui;
		this.width = width;
		this.height = height;
		this.splitWidth = splitWidth;
		
		init();
		initButtons();
		initButtonGroups();
		addButtons();
	}

	/**
	 * Initialize this the control panel.
	 */
	private void init() {
		setLayout(null);
		setBounds(2 * spacing + splitWidth, spacing, width, height);
		setBorder(BorderFactory.createRaisedBevelBorder());
	}

	/**
	 * Add the execution buttons and corresponding action listener.
	 */
	private void initButtons() {
		executeButton = new JButton("EXECUTE");
		executeButton.setBounds(spacing, spacing, 200, height - 2 * spacing);
		
		commitButton = new JButton("COMMIT");
		commitButton.setBounds(200 + 2 * spacing, spacing, 100, height - 2 * spacing);
		
		rollbackButton = new JButton("ROLLBACK");
		rollbackButton.setBounds(300 + 3 * spacing, spacing, 100, height - 2 * spacing);
		
		planButton = new JButton("SQL PLAN");
		planButton.setBounds(width - spacing - 150, spacing, 150, height - 2 * spacing);
		
		executeButton.addActionListener(new ExecutionListener(ui));
		commitButton.addActionListener(new ExecutionListener(ui));
		rollbackButton.addActionListener(new ExecutionListener(ui));
		planButton.addActionListener(new ExecutionListener(ui));
	}
	
	/**
	 * Initialize the radio buttons and button group.
	 */
	private void initButtonGroups() {
		basicButton = new JRadioButton("Basic");
		basicButton.setMnemonic(KeyEvent.VK_B);
		basicButton.setActionCommand("BASIC");
		basicButton.setSelected(true);

		typicalButton = new JRadioButton("Typical");
		typicalButton.setMnemonic(KeyEvent.VK_T);
		typicalButton.setActionCommand("TYPICAL");
		typicalButton.setSelected(false);

		allButton = new JRadioButton("All");
		allButton.setMnemonic(KeyEvent.VK_A);
		allButton.setActionCommand("ALL");
		allButton.setSelected(false);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(basicButton);
	    group.add(typicalButton);
	    group.add(allButton);
	    
	    int leftpadding = 3 * width / 5 ;
	    basicButton.setBounds(leftpadding, spacing, 70, height - 2 * spacing);
	    typicalButton.setBounds(leftpadding + 70 + spacing, spacing, 80, height - 2 * spacing);
	    allButton.setBounds(leftpadding + 2 * 70 + 2 * spacing, spacing, 60, height - 2 * spacing);
	    
	    ActionListener radioListener = new PlanOptionListener(); 
	    basicButton.addActionListener(radioListener);
	    typicalButton.addActionListener(radioListener);
	    allButton.addActionListener(radioListener);
	}
	
	private void addButtons() {
		add(executeButton);
		add(commitButton);
		add(rollbackButton);
		add(planButton);
		add(basicButton);
		add(typicalButton);
		add(allButton);
	}
}
